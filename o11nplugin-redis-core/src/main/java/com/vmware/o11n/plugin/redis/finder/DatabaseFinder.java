package com.vmware.o11n.plugin.redis.finder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vmware.o11n.plugin.redis.config.ConnectionRepository;
import com.vmware.o11n.plugin.redis.model.Connection;
import com.vmware.o11n.plugin.redis.model.Database;
import com.vmware.o11n.sdk.modeldriven.FoundObject;
import com.vmware.o11n.sdk.modeldriven.ObjectFinder;
import com.vmware.o11n.sdk.modeldriven.PluginContext;
import com.vmware.o11n.sdk.modeldriven.Sid;

public class DatabaseFinder implements ObjectFinder<Database> {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public Database find(PluginContext ctx, String type, Sid id) {
        Connection connection = connectionRepository.findLiveConnection(id);
        if (connection != null) {
            return connection.getDatabase((int) id.getLong("dbid", 0));
        }
        return null;
    }

    @Override
    public List<FoundObject<Database>> query(PluginContext ctx, String type, String query) {
        return null;
    }

    @Override
    public Sid assignId(Database obj, Sid relatedObject) {
        return relatedObject.with("dbid", obj.getIndex());
    }

}
