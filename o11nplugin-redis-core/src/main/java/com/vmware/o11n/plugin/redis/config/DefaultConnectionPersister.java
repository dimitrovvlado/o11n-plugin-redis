package com.vmware.o11n.plugin.redis.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vmware.o11n.plugin.redis.model.ConnectionInfo;
import com.vmware.o11n.sdk.modeldriven.Sid;

import ch.dunes.vso.sdk.endpoints.IEndpointConfiguration;
import ch.dunes.vso.sdk.endpoints.IEndpointConfigurationService;

@Component
public class DefaultConnectionPersister implements ConnectionPersister {

    private static final Logger log = LoggerFactory.getLogger(DefaultConnectionPersister.class);

    /**
     * A list of listeners, who have subscribed to any configuration events,
     * such as connection updates and deletions.
     */
    private final Collection<ConfigurationChangeListener> listeners;

    public DefaultConnectionPersister() {
        // Initialise the listeners
        listeners = new CopyOnWriteArrayList<ConfigurationChangeListener>();
    }

    /*
     * Constants of the key names under which the connection values will be
     * stored.
     */
    private static final String ID = "connectionId";
    private static final String NAME = "name";
    private static final String SERVICE_HOST = "serviceHost";
    private static final String SERVICE_PORT = "servicePort";

    /*
     * The platform-provided service for configuration persistence
     */
    @Autowired
    private IEndpointConfigurationService endpointConfigurationService;

    /*
     * Returns a collection of all stored configurations for this plug-in only
     * The service is aware of the plug-in name, thus will return only
     * configurations for this plug-in.
     */
    @Override
    public List<ConnectionInfo> findAll() {
        Collection<IEndpointConfiguration> configs;
        try {
            // Use the configuration service to retrieve all configurations.
            // The service is aware of the plug-in name, thus will return only
            // configurations for this plug-in.
            configs = endpointConfigurationService.getEndpointConfigurations();
            List<ConnectionInfo> result = new ArrayList<>(configs.size());

            // Iterate all the connections
            for (IEndpointConfiguration config : configs) {
                // Convert the IEndpointConfiguration to our domain object - the
                // ConnectionInfo
                ConnectionInfo connectionInfo = getConnectionInfo(config);
                if (connectionInfo != null) {
                    log.debug("Adding connection info to result map: " + connectionInfo);
                    result.add(connectionInfo);
                }
            }
            return result;
        } catch (IOException e) {
            log.debug("Error reading connections.", e);
            throw new RuntimeException(e);
        }
    }

    /*
     * Returns a ConnectionInfo by its ID The service is aware of the plug-in
     * name, thus cannot return a configuration for another plug-in.
     */
    @Override
    public ConnectionInfo findById(Sid id) {
        // Sanity checks
        Validate.notNull(id, "Sid cannot be null.");

        IEndpointConfiguration endpointConfiguration;
        try {
            // Use the configuration service to retrieve the configuration
            // service by its ID
            endpointConfiguration = endpointConfigurationService.getEndpointConfiguration(id.toString());

            // Convert the IEndpointConfiguration to our domain object - the
            // ConnectionInfo
            return getConnectionInfo(endpointConfiguration);
        } catch (IOException e) {
            log.debug("Error finding connection by id: " + id.toString(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Save or update a connection info. The service is aware of the plug-in
     * name, thus cannot save the configuration under the name of another
     * plug-in.
     */
    @Override
    public ConnectionInfo save(ConnectionInfo connectionInfo) {
        // Sanity checks
        Validate.notNull(connectionInfo, "Connection info cannot be null.");
        Validate.notNull(connectionInfo.getId(), "Connection info must have an id.");

        // Additional validation - in this case we want the name of the
        // connection to be unique
        validateConnectionName(connectionInfo);
        try {
            // Find a connection with the provided ID. We don't expect to have
            // an empty ID
            IEndpointConfiguration endpointConfiguration = endpointConfigurationService
                    .getEndpointConfiguration(connectionInfo.getId().toString());
            // If the configuration is null, then we are performing a save
            // operation
            if (endpointConfiguration == null) {
                // Use the configuration service to create a new (empty)
                // IEndpointConfiguration.
                // In this case, we are responsible for assigning the ID of the
                // configuration,
                // which is done in the constructor of the ConnectionInfo
                endpointConfiguration = endpointConfigurationService
                        .newEndpointConfiguration(connectionInfo.getId().toString());
            }

            // Convert the ConnectionInfo the IEndpointConfiguration
            addConnectionInfoToConfig(endpointConfiguration, connectionInfo);

            // Use the configuration service to save the endpoint configuration
            endpointConfigurationService.saveEndpointConfiguration(endpointConfiguration);

            // Fire an event to all subscribers, that we have updated a
            // configuration.
            // Pass the entire connectionInfo object and let the subscribers
            // decide if they need to do something
            fireConnectionUpdated(connectionInfo);
            return connectionInfo;
        } catch (IOException e) {
            log.error("Error saving connection " + connectionInfo, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a connection info. The service is aware of the plug-in name, thus
     * cannot delete a configuration from another plug-in.
     */
    @Override
    public void delete(ConnectionInfo connectionInfo) {
        try {
            // Use the configuration service to delete the connection info. The
            // service uses the ID
            endpointConfigurationService.deleteEndpointConfiguration(connectionInfo.getId().toString());

            // Fire an event to all subscribers, that we have deleted a
            // configuration.
            // Pass the entire connectionInfo object and let the subscribers
            // decide if they need to do something
            fireConnectionRemoved(connectionInfo);
        } catch (IOException e) {
            log.error("Error deleting endpoint configuration: " + connectionInfo, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to load the entire configuration set of the plug-in.
     * As a second step we fire a notification to all subscribers. This method
     * is used when the plug-in is being loaded (on server startup).
     */
    @Override
    public void load() {
        List<ConnectionInfo> findAll = findAll();
        for (ConnectionInfo connectionInfo : findAll) {
            fireConnectionUpdated(connectionInfo);
        }
    }

    /**
     * Attach a configuration listener which will be called when a connection is
     * created/updated/deleted
     */
    @Override
    public void addChangeListener(ConfigurationChangeListener listener) {
        listeners.add(listener);
    }

    /*
     * A helper method which iterates all event subscribers and fires the update
     * notification for the provided connection info.
     */
    private void fireConnectionUpdated(ConnectionInfo connectionInfo) {
        for (ConfigurationChangeListener li : listeners) {
            li.connectionUpdated(connectionInfo);
        }
    }

    /*
     * A helper method which iterates all event subscribers and fires the delete
     * notification for the provided connection info.
     */
    private void fireConnectionRemoved(ConnectionInfo connectionInfo) {
        for (ConfigurationChangeListener li : listeners) {
            li.connectionRemoved(connectionInfo);
        }
    }

    /*
     * A helper method which converts our domain object the ConnectionInfo to an
     * IEndpointConfiguration
     */
    private void addConnectionInfoToConfig(IEndpointConfiguration config, ConnectionInfo info) {
        try {
            config.setString(ID, info.getId().toString());
            config.setString(NAME, info.getName());
            config.setString(SERVICE_HOST, info.getHost());
            config.setInt(SERVICE_PORT, info.getPort());
        } catch (Exception e) {
            log.error("Error converting ConnectionInfo to IEndpointConfiguration.", e);
            throw new RuntimeException(e);
        }
    }

    /*
     * A helper method which converts the IEndpointConfiguration to our domain
     * object the ConnectionInfo
     */
    private ConnectionInfo getConnectionInfo(IEndpointConfiguration config) {
        ConnectionInfo info = null;
        try {
            Sid id = Sid.valueOf(config.getString(ID));
            info = new ConnectionInfo(id);
            info.setName(config.getString(NAME));
            info.setHost(config.getString(SERVICE_HOST));
            info.setPort(config.getAsInteger(SERVICE_PORT));
        } catch (IllegalArgumentException e) {
            log.warn("Cannot convert IEndpointConfiguration to ConnectionInfo: " + config.getId(), e);
        }
        return info;
    }

    /*
     * A helper method which validates if a connection with the same name
     * already exists.
     */
    private void validateConnectionName(ConnectionInfo connectionInfo) {
        ConnectionInfo configurationByName = getConfigurationByName(connectionInfo.getName());
        if (configurationByName != null
                && !configurationByName.getId().toString().equals(connectionInfo.getId().toString())) {
            throw new RuntimeException("Connection with the same name already exists: " + connectionInfo);
        }
    }

    /*
     * A helper method which gets a connection by name.
     */
    private ConnectionInfo getConfigurationByName(String name) {
        Validate.notNull(name, "Connection name cannot be null.");
        Collection<ConnectionInfo> findAllClientInfos = findAll();
        for (ConnectionInfo info : findAllClientInfos) {
            if (name.equals(info.getName())) {
                return info;
            }
        }
        return null;
    }

}
