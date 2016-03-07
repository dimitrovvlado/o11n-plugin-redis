package com.vmware.o11n.plugin.redis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vmware.o11n.sdk.modeldriven.Findable;
import com.vmware.o11n.sdk.modeldriven.Sid;
import com.vmware.o11n.sdk.modeldriven.extension.ExtensionMethod;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Qualifier(value = "connection")
@Scope(value = "prototype")
public class Connection implements Findable {

    private static final int DEFAULT_REDIS_DATABASE_INDEX = 0;

    /*
     * The connectionInfo which stands behind this live connection.
     */
    private ConnectionInfo connectionInfo;

    private Map<Integer, Database> databases = null;

    private JedisPool pool;

    /*
     * There is no default constructor, the Connection must be initialised only
     * with a connection info argument.
     */
    public Connection(ConnectionInfo info) {
        init(info);
    }

    @Override
    public Sid getInternalId() {
        return getConnectionInfo().getId();
    }

    @Override
    public void setInternalId(Sid id) {
        // do nothing, we set the Id in the constructor
    }

    public String getName() {
        return getConnectionInfo().getName();
    }

    public String getHost() {
        return getConnectionInfo().getHost();
    }

    public int getPort() {
        return getConnectionInfo().getPort();
    }

    public synchronized ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public String getDisplayName() {
        return getConnectionInfo().getName() + " [" + getConnectionInfo().getHost() + "]";
    }

    public List<Database> getDatabases() {
        if (databases == null) {
            databases = new HashMap<>(16);
            // Issue a call to Redis, to see how many databases are configured,
            // default is 16
            List<String> configs = getResource(DEFAULT_REDIS_DATABASE_INDEX).configGet("databases");
            int numberOfInstances = Integer.parseInt(configs.get(1));
            for (int index = 0; index < numberOfInstances; index++) {
                databases.put(index, new Database(this, index));
            }
        }
        return new ArrayList<>(databases.values());
    }

    @ExtensionMethod
    public Database getDatabase(int index) {
        return getDatabases().get(index);
    }

    @ExtensionMethod
    public Database getDefaultDatabase() {
        return getDatabase(DEFAULT_REDIS_DATABASE_INDEX);
    }

    /**
     * Returns a redis connection from the pool.
     *
     * @param index
     *            the index of the database
     */
    public Jedis getResource(int index) {
        Jedis resource = getPool().getResource();
        resource.select(index);
        return resource;
    }

    /*
     * Lazy initialization of the pool.
     */
    private synchronized JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig jedisConfig = new JedisPoolConfig();
            pool = new JedisPool(jedisConfig, connectionInfo.getHost(), connectionInfo.getPort());
        }
        return pool;
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
        if (pool != null) {
            pool.destroy();
        }
    }

}
