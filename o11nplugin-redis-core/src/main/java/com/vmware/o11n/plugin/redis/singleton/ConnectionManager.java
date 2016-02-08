package com.vmware.o11n.plugin.redis.singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.vmware.o11n.plugin.redis.config.ConnectionPersister;
import com.vmware.o11n.plugin.redis.model.ConnectionInfo;
import com.vmware.o11n.plugin.sdk.spring.platform.GlobalPluginNotificationHandler;

/**
 * A scripting singleton for managing redis connections.
 */
@Component
@Scope(value = "prototype")
public class ConnectionManager {

    @Autowired
    private ConnectionPersister persister;

    /*
     * A vRO SDK object which is responsible for notifying the platform of
     * changes in the inventory of the plug-in
     */
    @Autowired
    private GlobalPluginNotificationHandler notificationHandler;

    /**
     * This method creates a redis connection by the provided name, host and
     * port.
     *
     * @param connectionName
     *            the name of the connection, only one with the same name can
     *            exist
     * @param redisHost
     *            the redis host, cannot be null
     * @param redisPort
     *            redis port
     * @return the ID of the newly created connection
     */
    public String save(String connectionName, String redisHost, int redisPort) {
        Assert.notNull(connectionName, "Connection cannot name cannot be null.");
        Assert.notNull(redisHost, "Redis host cannot be null.");
        Assert.isTrue(redisPort > 0, "Redis port needs to be a positive number.");

        ConnectionInfo info = new ConnectionInfo();
        info.setName(connectionName);
        info.setHost(redisHost);
        info.setPort(redisPort);

        // Save the connection through the persister
        info = persister.save(info);

        // Invalidate all elements of the redis inventory
        notificationHandler.notifyElementsInvalidate();

        // Return the ID of the newly created connection
        return info.getId().toString();
    }

}
