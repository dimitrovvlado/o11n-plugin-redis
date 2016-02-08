package com.vmware.o11n.plugin.redis.config;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.vmware.o11n.plugin.redis.model.Connection;
import com.vmware.o11n.plugin.redis.model.ConnectionInfo;
import com.vmware.o11n.sdk.modeldriven.Sid;

@Component
public class ConnectionRepository implements ApplicationContextAware, InitializingBean, ConfigurationChangeListener {

    /*
     * Injecting the ConnectionPersister
     */
    @Autowired
    private ConnectionPersister persister;

    private ApplicationContext context;

    /*
     * The local map (cache) of live connections
     */
    private final Map<Sid, Connection> connections;

    public ConnectionRepository() {
        connections = new ConcurrentHashMap<Sid, Connection>();
    }

    /**
     * Returns a live connection by its ID or null if no such connection has
     * been initialised by this ID
     */
    public Connection findLiveConnection(Sid anyId) {
        return connections.get(anyId.getId());
    }

    /**
     * Returns all live connections from the local cache
     */
    public Collection<Connection> findAll() {
        return connections.values();
    }

    /*
     * Spring-specifics - storing a reference to the spring context
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    /*
     * Spring specifics - this method is being called automatically by the
     * spring container after all the fields are set and before the bean is
     * being provided for usage. This method will be called when the plug-in is
     * being loaded - on server start-up.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // Subscribing the Repository for any configuration changes that occur
        // in the Persister
        persister.addChangeListener(this);

        // Initialising the Persister. By doing that, the persister will invoke
        // the connectionUpdated() method
        // and since we are subscribed to those events, the local cache will be
        // populated with all the available connections.
        persister.load();
    }

    private Connection createConnection(ConnectionInfo info) {
        // This call will create a new spring-managed bean from the context
        return (Connection) context.getBean("connection", info);
    }

    /*
     * This method will be called from the ConnectionPersister when a new
     * connection is added or an existing one is updated.
     */
    @Override
    public void connectionUpdated(ConnectionInfo info) {
        Connection live = connections.get(info.getId());
        if (live != null) {
            live.update(info);
        } else {
            // connection just added, create it
            live = createConnection(info);
            connections.put(info.getId(), live);
        }
    }

    /*
     * This method will be called from the ConnectionPersister when a connection
     * is removed.
     */
    @Override
    public void connectionRemoved(ConnectionInfo info) {
        Connection live = connections.remove(info.getId());
        if (live != null) {
            live.destroy();
        }
    }
}
