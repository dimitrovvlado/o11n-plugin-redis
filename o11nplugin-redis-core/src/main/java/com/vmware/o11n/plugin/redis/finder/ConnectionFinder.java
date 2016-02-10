package com.vmware.o11n.plugin.redis.finder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vmware.o11n.plugin.redis.config.ConnectionRepository;
import com.vmware.o11n.plugin.redis.model.Connection;
import com.vmware.o11n.sdk.modeldriven.FoundObject;
import com.vmware.o11n.sdk.modeldriven.ObjectFinder;
import com.vmware.o11n.sdk.modeldriven.PluginContext;
import com.vmware.o11n.sdk.modeldriven.Sid;

public class ConnectionFinder implements ObjectFinder<Connection> {

    @Autowired
    private ConnectionRepository connectionRepository;

    /*
     * When a connection is found, we need to assign an ID. However, the
     * Connection has its own ID, so we don't need to do anything here, aside
     * from return the connection.
     */
    @Override
    public Sid assignId(Connection obj, Sid relatedObject) {
        return obj.getInternalId();
    }

    /*
     * Finds the connection by ID
     */
    @Override
    public Connection find(PluginContext ctx, String type, Sid id) {
        return connectionRepository.findLiveConnection(id);
    }

    @Override
    public List<FoundObject<Connection>> query(PluginContext ctx, String type, String query) {
        Collection<Connection> allConnections = connectionRepository.findAll();
        List<FoundObject<Connection>> result = new LinkedList<>();
        boolean returnAll = "".equals(query);
        for (Connection connection : allConnections) {
            if (returnAll || connection.getName().toLowerCase().startsWith(query.toLowerCase())) {
                FoundObject<Connection> foundObject = new FoundObject<>(connection);
                result.add(foundObject);
            }
        }
        return result;
    }

}
