package com.vmware.o11n.plugin.redis.model;

import org.springframework.util.Assert;

import com.vmware.o11n.sdk.modeldriven.extension.ExtensionMethod;

import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

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
    public long strlen(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.strlen(key);
        }
    }

    @ExtensionMethod
    public long zadd(String key, Hashtable<String, Double> scoreMembers, ZAddParams params) {
        try (Jedis jedis = connection.getResource(index)) {
            if (params != null && scoreMembers != null) {
                return jedis.zadd(key, scoreMembers, params);
            } else if (scoreMembers != null) {
                return jedis.zadd(key, scoreMembers);
            }
            return jedis.strlen(key);
        }
    }

    @ExtensionMethod
    public Set<String> zrange(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrange(key, start, end);
        }
    }

    @ExtensionMethod
    public long zrem(String key, String[] members) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrem(key, members);
        }
    }

    @ExtensionMethod
    public double zincrby(String key, double score, String member, ZIncrByParams params) {
        try (Jedis jedis = connection.getResource(index)) {
            if (params != null) {
                return jedis.zincrby(key, score, member, params);
            }
            return jedis.zincrby(key, score, member);
        }
    }

    @ExtensionMethod
    public long zrank(String key, String member) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrank(key, member);
        }
    }

    @ExtensionMethod
    public long zrevrank(String key, String member) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrevrank(key, member);
        }
    }

    @ExtensionMethod
    public Set<String> zrevrange(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrevrange(key, start, end);
        }
    }

    @ExtensionMethod
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrangeWithScores(key, start, end);
        }
    }

    @ExtensionMethod
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zrevrangeWithScores(key, start, end);
        }
    }

    @ExtensionMethod
    public long zcard(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zcard(key);
        }
    }

    @ExtensionMethod
    public double zscore(String key, String member) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zscore(key, member);
        }
    }

    @ExtensionMethod
    public List<String> sort(String key, SortingParams sortingParams) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.sort(key, sortingParams);
        }
    }

    @ExtensionMethod
    public long zcount(String key, double min, double max) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zcount(key, min, max);
        }
    }

    @ExtensionMethod
    public Set<String> zrangeByScore(String key, double min, double max, Integer offset, Integer count) {
        try (Jedis jedis = connection.getResource(index)) {
            if (offset != null && count != null) {
                return jedis.zrangeByScore(key, min, max, offset, count);
            }
            return jedis.zrangeByScore(key, min, max);
        }
    }

    @ExtensionMethod
    public Set<String> zrevrangeByScore(String key, double max, double min, Integer offset, Integer count) {
        try (Jedis jedis = connection.getResource(index)) {
            if (offset != null && count != null) {
                return jedis.zrevrangeByScore(key, max, min, offset, count);
            }
            return jedis.zrevrangeByScore(key, min, max);
        }
    }

    @ExtensionMethod
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, Integer offset, Integer count) {
        try (Jedis jedis = connection.getResource(index)) {
            if (offset != null && count != null) {
                return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
            }
            return jedis.zrangeByScoreWithScores(key, min, max);
        }
    }

    @ExtensionMethod
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, Integer offset, Integer count) {
        try (Jedis jedis = connection.getResource(index)) {
            if (offset != null && count != null) {
                return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            }
            return jedis.zrevrangeByScoreWithScores(key, max, min);
        }
    }

    @ExtensionMethod
    public long zremrangeByRank(String key, long start, long end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zremrangeByRank(key, start, end);
        }
    }

    @ExtensionMethod
    public long zremrangeByScore(String key, double start, double end) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zremrangeByScore(key, start, end);
        }
    }

    @ExtensionMethod
    public long zlexcount(String key, String min, String max) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zlexcount(key, min, max);
        }
    }

    @ExtensionMethod
    public Set<String> zrangeByLex(String key, String min, String max, Integer offset, Integer count) {
        try (Jedis jedis = connection.getResource(index)) {
            if (offset != null && count != null) {
                return jedis.zrangeByLex(key, min, max, offset, count);
            }
            return jedis.zrangeByLex(key, min, max);
        }
    }

    @ExtensionMethod
    public Set<String> zrevrangeByLex(String key, String max, String min, Integer offset, Integer count) {
        try (Jedis jedis = connection.getResource(index)) {
            if (offset != null && count != null) {
                return jedis.zrevrangeByLex(key, max, min, offset, count);
            }
            return jedis.zrevrangeByLex(key, max, min);
        }
    }

    @ExtensionMethod
    public long zremrangeByLex(String key, String min, String max) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.zremrangeByLex(key, min, max);
        }
    }

    @ExtensionMethod
    public long linsert(String key, Client.LIST_POSITION where, String pivot, String value) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.linsert(key, where, pivot, value);
        }
    }

    @ExtensionMethod
    public List<String> blpop(int timeout, String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.blpop(timeout, key);
        }
    }

    @ExtensionMethod
    public List<String> brpop(int timeout, String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.brpop(timeout, key);
        }
    }

    @ExtensionMethod
    public String echo(String string) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.echo(string);
        }
    }

    /**
     * Move the specified key from the currently selected DB to the specified destination DB. Note
     * that this command returns 1 only if the key was successfully moved, and 0 if the target key was
     * already there or if the source key was not found at all, so it is possible to use MOVE as a
     * locking primitive.
     * @param key
     * @param dbIndex
     * @return Integer reply, specifically: 1 if the key was moved 0 if the key was not moved because
     *         already present on the target DB or was not found in the current DB.
     */
    @ExtensionMethod
    public long move(String key, int dbIndex) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.move(key, dbIndex);
        }
    }

    @ExtensionMethod
    public long bitcount(String key, Long start, Long end) {
        try (Jedis jedis = connection.getResource(index)) {
            if (start != null && end != null) {
                return jedis.bitcount(key, start, end);
            }
            return jedis.bitcount(key);
        }
    }

    @ExtensionMethod
    public long bitpos(String key, boolean value, BitPosParams params) {
        try (Jedis jedis = connection.getResource(index)) {
            if (params != null) {
                return jedis.bitpos(key, value, params);
            }
            return jedis.bitpos(key, value);
        }
    }

    @ExtensionMethod
    public ScanResult hscan(String key, String cursor, ScanParams params) {
        try (Jedis jedis = connection.getResource(index)) {
            if (params != null) {
                return jedis.hscan(key, cursor, params);
            }
            return jedis.hscan(key, cursor);
        }
    }

    @ExtensionMethod
    public ScanResult zscan(String key, String cursor, ScanParams params) {
        try (Jedis jedis = connection.getResource(index)) {
            if (params != null) {
                return jedis.zscan(key, cursor, params);
            }
            return jedis.zscan(key, cursor);
        }
    }

    @ExtensionMethod
    public long pfcount(String key) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.pfcount(key);
        }
    }

    @ExtensionMethod
    public long pfadd(String key, String[] elements) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.pfadd(key, elements);
        }
    }

    @ExtensionMethod
    public long geoadd(String key, double longitude, double latitude, String member) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.geoadd(key, longitude, latitude, member);
        }
    }

    @ExtensionMethod
    public double geodist(String key, String member1, String member2, GeoUnit unit) {
        try (Jedis jedis = connection.getResource(index)) {
            if (unit != null) {
                return jedis.geodist(key, member1, member2, unit);
            }
            return jedis.geodist(key, member1, member2);
        }
    }

    @ExtensionMethod
    public List<String> geohash(String key, String[] members) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.geohash(key, members);
        }
    }

    @ExtensionMethod
    public List<GeoCoordinate> geopos(String key, String[] members) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.geopos(key, members);
        }
    }

    @ExtensionMethod
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit
            unit, GeoRadiusParam param) {
        try (Jedis jedis = connection.getResource(index)) {
            if (param != null) {
                return jedis.georadius(key, longitude, latitude, radius, unit, param);
            }
            return jedis.georadius(key, longitude, latitude, radius, unit);
        }
    }

    @ExtensionMethod
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit,
                                                     GeoRadiusParam param) {
        try (Jedis jedis = connection.getResource(index)) {
            if (param != null) {
                return jedis.georadiusByMember(key, member, radius, unit, param);
            }
            return jedis.georadiusByMember(key, member, radius, unit);
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

    @ExtensionMethod
    public Object eval(String script, List<String> keys, List<String> args) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.eval(script, keys, args);
        }
    }

    @ExtensionMethod
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.evalsha(sha1, keys, args);
        }
    }

    @ExtensionMethod
    public String scriptLoad(String script) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.scriptLoad(script);
        }
    }

    @ExtensionMethod
    public boolean scriptExists(String sha1) {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.scriptExists(sha1);
        }
    }

    @ExtensionMethod
    public void scriptKill() {
        try (Jedis jedis = connection.getResource(index)) {
            jedis.scriptKill();
        }
    }

    @ExtensionMethod
    public String scriptFlush() {
        try (Jedis jedis = connection.getResource(index)) {
            return jedis.scriptFlush();
        }
    }
}
