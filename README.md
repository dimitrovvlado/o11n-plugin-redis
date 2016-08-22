# Redis plug-in for vRealize Orchestrator

The Redis plug-in for vRealize Orchestrator is a a plug-in which provides integration capabilities with multiple [Redis](http://redis.io/) instances.

### Plugin download
[o11nplugin-redis-1.0.0.vmoapp](https://github.com/dimitrovvlado/o11n-plugin-cache/blob/master/dist/o11nplugin-cache.vmoapp?raw=true) 

### Supported platform version
The Redis plug-in supports vRO 6.0 and later.

### Sample scripting

```javascript
//Store a simple key-value in redis
var statusCode = connection.defaultDatabase.set("test-key", "test-value");
//Retrieve value
var value = connection.defaultDatabase.get("test-key");
```

### Dependencies
The Redis plug-in for vRO uses the following third-party libraries:
* [Jedis](https://github.com/xetorthio/jedis) - a java-based Redis client.
* [Google Guava](https://github.com/google/guava) - core libraries for Java-based projects, distributed under the Apache License, Version 2.0
* [Apache Commons Pool](https://commons.apache.org/proper/commons-pool/download_pool.cgi) - a library for object pooling