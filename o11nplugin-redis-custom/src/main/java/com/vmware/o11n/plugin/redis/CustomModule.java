package com.vmware.o11n.plugin.redis;



import java.util.Collections;

import com.google.inject.AbstractModule;
import com.vmware.o11n.sdk.modeldrivengen.mapping.AbstractMapping;
import com.vmware.o11n.sdk.modeldrivengen.model.Plugin;

public class CustomModule extends AbstractModule {

    private final Plugin plugin;

    /**
     * Binds the CustomMapping class to the plugin instance
     */
    @Override
    protected void configure() {
        bind(AbstractMapping.class).toInstance(new CustomMapping());
        bind(Plugin.class).toInstance(plugin);

    }

    public CustomModule() {
        this.plugin = new Plugin();

        plugin.setApiPrefix("Redis");
        plugin.setIcon("redis.png");
        plugin.setDescription("Redis plug-in for vRO");
        plugin.setDisplayName("Redis");
        plugin.setName("Redis");
        plugin.setPackages(Collections.singletonList("o11nplugin-redis-package-${project.version}.package"));
        plugin.setAdaptorClassName(com.vmware.o11n.plugin.redis.RedisPluginAdaptor.class);
    }
}