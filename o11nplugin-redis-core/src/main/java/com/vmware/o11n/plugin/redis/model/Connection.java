package com.vmware.o11n.plugin.redis.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "connection")
@Scope(value = "prototype")
public class Connection {
    /*
     * The connectionInfo which stands behind this live connection.
     */
    private ConnectionInfo connectionInfo;

    /*
     * There is no default constructor, the Connection must be initialised only
     * with a connection info argument.
     */
    public Connection(ConnectionInfo info) {
        init(info);
    }

    public synchronized ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public String getDisplayName() {
        return getConnectionInfo().getName() + " [" + getConnectionInfo().getHost() + "]";
    }

    /*
     * Updates this connection with the provided info. This operation will
     * destroy the existing third party client, causing all associated
     * operations to fail.
     */
    public synchronized void update(ConnectionInfo connectionInfo) {
        if (this.connectionInfo != null && !connectionInfo.getId().equals(this.connectionInfo.getId())) {
            throw new IllegalArgumentException("Cannot update using different id");
        }
        destroy();
        init(connectionInfo);
    }

    private void init(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public synchronized void destroy() {
        // Destroy the third party client
    }
}
