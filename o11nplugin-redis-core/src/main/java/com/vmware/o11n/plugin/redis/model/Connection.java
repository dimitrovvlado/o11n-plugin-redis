package com.vmware.o11n.plugin.redis.model;

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
    /*
     * The connectionInfo which stands behind this live connection.
     */
    private ConnectionInfo connectionInfo;

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

    /**
     * Returns a redis connection from the pool.
     */
    public Jedis getResource() {
        return getPool().getResource();
    }

    @ExtensionMethod
    public String ping() {
        try (Jedis jedis = getResource()) {
            return jedis.ping();
        }
    }

    /**
     * If the key already exists and is a string, this command appends the
     * provided value at the end of the string. If the key does not exist it is
     * created and set as an empty string, so APPEND will be very similar to SET
     * in this special case.
     * <p>
     * Time complexity: O(1). The amortized time complexity is O(1) assuming the
     * appended value is small and the already present value is of any size,
     * since the dynamic string library used by Redis will double the free space
     * available on every reallocation.
     *
     * @return Integer reply, specifically the total length of the string after
     *         the append operation.
     */
    @ExtensionMethod
    public Long append(String key, String value) {
        try (Jedis jedis = getResource()) {
            return jedis.append(key, value);
        }
    }

    @ExtensionMethod
    public String set(String key, String value, String nxxx, String expx, Integer time) {
        try (Jedis jedis = getResource()) {
            if (expx != null && time != null) {
                return jedis.set(key, value, nxxx, expx, time);
            }
            if (nxxx != null) {
                return jedis.set(key, value, nxxx);
            }

            return jedis.set(key, value);
        }
    }

    @ExtensionMethod
    public String get(String key) {
        try (Jedis jedis = getResource()) {
            return jedis.get(key);
        }
    }

    @ExtensionMethod
    public Boolean exists(String key) {
        try (Jedis jedis = getResource()) {
            return jedis.exists(key);
        }
    }

    @ExtensionMethod
    public Long exists(String[] key) {
        try (Jedis jedis = getResource()) {
            return jedis.exists(key);
        }
    }

    @ExtensionMethod
    public String[] blpop(int timeout, String[] args) {
        try (Jedis jedis = getResource()) {
            return jedis.blpop(timeout, args).toArray(new String[0]);
        }
    }

    @ExtensionMethod
    public Long rpush(String key, String[] strings) {
        try (Jedis jedis = getResource()) {
            return jedis.rpush(key, strings);
        }
    }

    @ExtensionMethod
    public Long rpushx(String key, String[] strings) {
        try (Jedis jedis = getResource()) {
            return jedis.rpushx(key, strings);
        }
    }

    @ExtensionMethod
    public Long lpush(String key, String[] strings) {
        try (Jedis jedis = getResource()) {
            return jedis.lpush(key, strings);
        }
    }

    @ExtensionMethod
    public Long lpushx(String key, String[] strings) {
        try (Jedis jedis = getResource()) {
            return jedis.lpushx(key, strings);
        }
    }

    @ExtensionMethod
    public Long incr(String key) {
        try (Jedis jedis = getResource()) {
            return jedis.incr(key);
        }
    }

    @ExtensionMethod
    public Long del(String key) {
        try (Jedis jedis = getResource()) {
            return jedis.del(key);
        }
    }

    @ExtensionMethod
    public String type(String key) {
        try (Jedis jedis = getResource()) {
            return jedis.type(key);
        }
    }

    @ExtensionMethod
    public String randomKey() {
        try (Jedis jedis = getResource()) {
            return jedis.randomKey();
        }
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
