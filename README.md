# Redis plug-in for vRealize Orchestrator

The Redis plug-in for vRealize Orchestrator is a a plug-in which provides integration capabilities with multiple [Redis](http://redis.io/) instances.

Plug-in features:

 * Operations with hashes, lists, sets, sorted sets, geo locations
 * Operations for scripting with Lua
 * Cluster configurations are currently not supported

### Plugin download
[o11nplugin-redis-1.0.0.vmoapp](https://github.com/dimitrovvlado/o11n-plugin-redis/blob/master/dist/o11nplugin-redis.vmoapp?raw=true) 

### Supported platform version
The Redis plug-in supports vRO 6.0 and later.

### Sample scripting

#####Basic operations
```javascript
//Store a simple key-value in redis
var statusCode = connection.defaultDatabase.set("test-key", "test-value");
//Retrieve value
var value = connection.defaultDatabase.get("test-key");
```

#####Scripting
```javascript
var script = "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}";
//Evaluate script
var result = connection.defaultDatabase.eval(script, ["key1", "key2"], ["arg1", "arg2"]);
//Load and evaluate script by sha
var sha = connection.defaultDatabase.scriptLoad(script);
result = connection.defaultDatabase.evalsha(sha, ["key1", "key2"], ["arg1", "arg2"]);
```

### Dependencies
The Redis plug-in for vRO uses the following third-party libraries:
* [Jedis](https://github.com/xetorthio/jedis) - a java-based Redis client.
* [Google Guava](https://github.com/google/guava) - core libraries for Java-based projects, distributed under the Apache License, Version 2.0
* [Apache Commons Pool](https://commons.apache.org/proper/commons-pool/download_pool.cgi) - a library for object pooling