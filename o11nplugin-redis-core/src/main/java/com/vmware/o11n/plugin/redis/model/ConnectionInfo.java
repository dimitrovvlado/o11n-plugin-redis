package com.vmware.o11n.plugin.redis.model;

import org.springframework.util.Assert;

import com.vmware.o11n.sdk.modeldriven.Sid;

/**
 * Object for all the redis connection details.
 */
public class ConnectionInfo {

    /**
     * Name of the connection as defined by the user when creating a connection
     */
    private String name;

    /**
     * ID of the connection
     */
    private final Sid id;

    /**
     * Service URI of the third party system
     */
    private String host;

    /**
     * Port of the connection, defaults the to redis default port number
     */
    private int port = 6379;

    public ConnectionInfo() {
        this.id = Sid.unique();
    }

    /*
     * Verify that each ConnectionInfo has an ID.
     */
    public ConnectionInfo(Sid id) {
        super();
        Assert.notNull(id, "Id cannot be null.");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Sid getId() {
        return id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "ConnectionInfo [name=" + name + ", id=" + id + ", uri=" + host + ", port=" + port + "]";
    }

}
