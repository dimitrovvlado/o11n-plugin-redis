package com.vmware.o11n.plugin.redis.model;

import org.springframework.util.Assert;

import com.vmware.o11n.sdk.modeldriven.extension.ExtensionMethod;

import redis.clients.jedis.Jedis;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public long persist(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.persist(key);
        }
    }

    @ExtensionMethod
    public long expire(String key, int seconds) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.expire(key, seconds);
        }
    }

    @ExtensionMethod
    public long pexpire(String key, long milliseconds) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.pexpire(key, milliseconds);
        }
    }

    @ExtensionMethod
    public long expireAt(String key, long unixTime) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.expireAt(key, unixTime);
        }
    }

    @ExtensionMethod
    public long pexpireAt(String key, long millisecondsTimestamp) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.pexpireAt(key, millisecondsTimestamp);
        }
    }

    @ExtensionMethod
    public long ttl(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.ttl(key);
        }
    }

    @ExtensionMethod
    public long pttl(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.pttl(key);
        }
    }

    @ExtensionMethod
    public boolean setbit(String key, long offset, boolean value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.setbit(key, offset, value);
        }
    }

    @ExtensionMethod
    public boolean setbit(String key, long offset) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.getbit(key, offset);
        }
    }

    @ExtensionMethod
    public long setrange(String key, long offset, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.setrange(key, offset, value);
        }
    }

    @ExtensionMethod
    public String getrange(String key, long startOffset, long endOffset) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.getrange(key, startOffset, endOffset);
        }
    }

    @ExtensionMethod
    public String getSet(String key, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.getSet(key, value);
        }
    }

    @ExtensionMethod
    public long setnx(String key, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.setnx(key, value);
        }
    }

    @ExtensionMethod
    public String setex(String key, int seconds, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.setex(key, seconds, value);
        }
    }

    @ExtensionMethod
    public String psetex(String key, long milliseconds, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.psetex(key, milliseconds, value);
        }
    }

    @ExtensionMethod
    public long decrBy(String key, long integer) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.decrBy(key, integer);
        }
    }

    @ExtensionMethod
    public long decr(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.decr(key);
        }
    }

    @ExtensionMethod
    public long incrBy(String key, long integer) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.incrBy(key, integer);
        }
    }

    @ExtensionMethod
    public double incrByFloat(String key, double value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.incrByFloat(key, value);
        }
    }

    @ExtensionMethod
    public String substr(String key, int start, int end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.substr(key, start, end);
        }
    }

    @ExtensionMethod
    public long hset(String key, String field, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hset(key, field, value);
        }
    }

    @ExtensionMethod
    public String hget(String key, String field) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hget(key, field);
        }
    }

    @ExtensionMethod
    public long hsetnx(String key, String field, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hsetnx(key, field, value);
        }
    }

    @ExtensionMethod
    public String hmset(String key, Hashtable<String, String> hash) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hmset(key, hash);
        }
    }

    @ExtensionMethod
    public List<String> hmget(String key, String[] fields) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hmget(key, fields);
        }
    }

    @ExtensionMethod
    public long hincrBy(String key, String field, long value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hincrBy(key, field, value);
        }
    }

    @ExtensionMethod
    public double hincrBy(String key, String field, double value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hincrByFloat(key, field, value);
        }
    }

    @ExtensionMethod
    public boolean hexists(String key, String field) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hexists(key, field);
        }
    }

    @ExtensionMethod
    public long hdel(String key, String[] fields) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hdel(key, fields);
        }
    }

    @ExtensionMethod
    public long hlen(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hlen(key);
        }
    }

    @ExtensionMethod
    public Set<String> hkeys(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hkeys(key);
        }
    }

    @ExtensionMethod
    public List<String> hvals(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.hvals(key);
        }
    }

    @ExtensionMethod
    public Hashtable<String, String> hgetAll(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return new Hashtable<String, String>(jedis.hgetAll(key));
        }
    }

    @ExtensionMethod
    public long llen(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.llen(key);
        }
    }

    @ExtensionMethod
    public List<String> lrange(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lrange(key, start, end);
        }
    }

    @ExtensionMethod
    public String ltrim(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.ltrim(key, start, end);
        }
    }

    @ExtensionMethod
    public String lindex(String key, long lindex) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lindex(key, lindex);
        }
    }

    @ExtensionMethod
    public String lset(String key, long lindex, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lset(key, lindex, value);
        }
    }

    @ExtensionMethod
    public long lrem(String key, long count, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lrem(key, count, value);
        }
    }

    @ExtensionMethod
    public String lpop(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lpop(key);
        }
    }

    @ExtensionMethod
    public String rpop(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.rpop(key);
        }
    }

    @ExtensionMethod
    public long sadd(String key, String[] members) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.sadd(key, members);
        }
    }

    @ExtensionMethod
    public long srem(String key, String[] members) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.srem(key, members);
        }
    }

    @ExtensionMethod
    public Set<String> smembers(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.smembers(key);
        }
    }

    @ExtensionMethod
    public String spop(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.spop(key);
        }
    }

    @ExtensionMethod
    public long scard(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.scard(key);
        }
    }

    @ExtensionMethod
    public boolean sismember(String key, String member) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.sismember(key, member);
        }
    }

    @ExtensionMethod
    public String srandmember(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.srandmember(key);
        }
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
    public boolean exists(String key) {
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
    public long rpush(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.rpush(key, strings);
        }
    }

    @ExtensionMethod
    public long rpushx(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.rpushx(key, strings);
        }
    }

    @ExtensionMethod
    public long lpush(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lpush(key, strings);
        }
    }

    @ExtensionMethod
    public long lpushx(String key, String[] strings) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.lpushx(key, strings);
        }
    }

    @ExtensionMethod
    public long incr(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.incr(key);
        }
    }

    @ExtensionMethod
    public long del(String key) {
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
