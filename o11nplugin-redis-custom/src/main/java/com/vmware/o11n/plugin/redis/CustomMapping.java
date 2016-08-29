package com.vmware.o11n.plugin.redis;

import com.vmware.o11n.plugin.redis.finder.ConnectionFinder;
import com.vmware.o11n.plugin.redis.finder.DatabaseFinder;
import com.vmware.o11n.plugin.redis.model.Connection;
import com.vmware.o11n.plugin.redis.model.Database;
import com.vmware.o11n.plugin.redis.relater.ConnectionHasDatabases;
import com.vmware.o11n.plugin.redis.relater.RootHasConnections;
import com.vmware.o11n.plugin.redis.singleton.ConnectionManager;
import com.vmware.o11n.sdk.modeldrivengen.mapping.AbstractMapping;
import com.vmware.o11n.sdk.modeldrivengen.mapping.MethodSelector;
import redis.clients.jedis.*;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

public class CustomMapping extends AbstractMapping {

    @Override
    public void define() {
      //@formatter:off

        convertWellKnownTypes();

        singleton(ConnectionManager.class);

        wrap(Connection.class).
            andFind().
            using(ConnectionFinder.class).
            withIcon("connection.png");

        wrap(Database.class).
            andFind().
            using(DatabaseFinder.class).
            withIcon("database.png");

        wrap(ZAddParams.class);
        wrap(ZIncrByParams.class);
        wrap(Tuple.class).hiding(new MethodSelector<Tuple>() {
            @Override
            public void select(Tuple tuple) {
                tuple.getBinaryElement();
            }
        });
        wrap(SortingParams.class).hiding(new MethodSelector<SortingParams>() {
            @Override
            public void select(SortingParams sortingParams) {
                sortingParams.get(new byte[0][0]);
            }
        });
        wrap(BitPosParams.class);
        wrap(ScanParams.class);
        wrap(ScanResult.class);
        wrap(GeoUnit.class);
        wrap(GeoCoordinate.class);

        enumerate(BinaryClient.LIST_POSITION.class).as("ListPosition");

        relateRoot().
            to(Connection.class).
            using(RootHasConnections.class).
            as("connections");

        relate(Connection.class).
            to(Database.class).
            using(ConnectionHasDatabases.class).
            as("databases");

      //@formatter:on
    }
}