package com.vmware.o11n.plugin.redis.singleton;

/**
 * A scripting singleton for managing redis connections.
 */
public class ConnectionManager {

    /**
     * This method creates a redis connection by the provided name, host and
     * port.
     *
     * @param connectionName
     *            the name of the connection, only one with the same name can
     *            exist
     * @param redisHost
     *            the redis host, cannot be null
     * @param redisPort
     *            redis port
     * @return the ID of the newly created connection
     */
    public String save(String connectionName, String redisHost, int redisPort) {
        return null;
    }

}
