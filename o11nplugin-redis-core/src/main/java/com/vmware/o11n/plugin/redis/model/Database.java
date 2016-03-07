package com.vmware.o11n.plugin.redis.model;

import org.springframework.util.Assert;

import com.vmware.o11n.sdk.modeldriven.extension.ExtensionMethod;

import redis.clients.jedis.Jedis;

public class Database {

    private final Connection connection;
    private final int index;

    public Database(Connection connection, int index) {
        Assert.notNull(connection, "Connection cannot be null.");
        Assert.isTrue(index >= 0, "Index must be a positive number.");

        this.connection = connection;
        this.index = index;
    }

    public String getDisplayName() {
        return "db" + index;
    }

    public int getIndex() {
        return index;
    }

    @ExtensionMethod
    public String ping() {
        try (Jedis jedis = connection.getResource(index)) {
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
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.append(key, value);
        }
    }

    @ExtensionMethod
    public String set(String key, String value, String nxxx, String expx, Integer time) {
        try (Jedis jedis = connection.getResource(index)) {
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
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.get(key);
        }
    }

    @ExtensionMethod
    public Boolean exists(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.exists(key);
        }
    }

    @ExtensionMethod
    public Long exists(String[] key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.exists(key);
        }
    }

    @ExtensionMethod
    public String[] blpop(int timeout, String[] args) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.blpop(timeout, args).toArray(new String[0]);
        }
    }

    @ExtensionMethod
    public Long rpush(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.rpush(key, strings);
        }
    }

    @ExtensionMethod
    public Long rpushx(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.rpushx(key, strings);
        }
    }

    @ExtensionMethod
    public Long lpush(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lpush(key, strings);
        }
    }

    @ExtensionMethod
    public Long lpushx(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lpushx(key, strings);
        }
    }

    @ExtensionMethod
    public Long incr(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.incr(key);
        }
    }

    @ExtensionMethod
    public Long del(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.del(key);
        }
    }

    @ExtensionMethod
    public String type(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.type(key);
        }
    }

    @ExtensionMethod
    public String randomKey() {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.randomKey();
        }
    }
}
