package fi.cdfdb.configuration;

import java.util.Optional;

public class CfConfiguration {

    public final String BIND_ADDRESS;
    public final Integer PORT;
    public final Boolean HEARTBEAT_ENABLED;
    public final Integer HEARTBEAT_INTERVAL_MS;

    public CfConfiguration(CfConfigurationReader reader) {
        this.BIND_ADDRESS = reader.readString(CfConfigurationKey.BIND_ADDRESS);
        this.PORT = reader.readInteger(CfConfigurationKey.PORT);
        this.HEARTBEAT_ENABLED = reader.readBoolean(CfConfigurationKey.HEARTBEAT_ENABLED);
        this.HEARTBEAT_INTERVAL_MS = reader.readInteger(CfConfigurationKey.HEARTBEAT_INTERVAL_MS);
    }
}
