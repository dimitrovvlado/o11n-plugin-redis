package com.vmware.o11n.plugin.redis.config;

import com.vmware.o11n.plugin.redis.model.ConnectionInfo;

/**
 * An extension point of the configuration persister. Serves the role of an
 * Observer when a certain connection is created, modified or deleted.
 */
public interface ConfigurationChangeListener {

    /**
     * Invoked when a connection is updated or created
     */
    void connectionUpdated(ConnectionInfo info);

    /**
     * Invoked when the ConnectionInfo input is deleted
     */
    void connectionRemoved(ConnectionInfo info);
}
