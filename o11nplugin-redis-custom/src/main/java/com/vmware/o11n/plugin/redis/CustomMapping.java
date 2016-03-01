package com.vmware.o11n.plugin.redis;

import com.vmware.o11n.plugin.redis.finder.ConnectionFinder;
import com.vmware.o11n.plugin.redis.model.Connection;
import com.vmware.o11n.plugin.redis.relater.RootHasConnections;
import com.vmware.o11n.plugin.redis.singleton.ConnectionManager;
import com.vmware.o11n.sdk.modeldrivengen.mapping.AbstractMapping;

public class CustomMapping extends AbstractMapping {

    @Override
    public void define() {
      //@formatter:off

        singleton(ConnectionManager.class);

        wrap(Connection.class).
            andFind().
            using(ConnectionFinder.class).
            withIcon("connection.png");

        relateRoot().
            to(Connection.class).
            using(RootHasConnections.class).
            as("connections");

      //@formatter:on
    }
}