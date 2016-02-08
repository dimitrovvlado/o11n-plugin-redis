package com.vmware.o11n.plugin.redis;

import com.vmware.o11n.plugin.redis.singleton.ConnectionManager;
import com.vmware.o11n.sdk.modeldrivengen.mapping.AbstractMapping;

public class CustomMapping extends AbstractMapping {

    @Override
    public void define() {
        singleton(ConnectionManager.class);
    }
}