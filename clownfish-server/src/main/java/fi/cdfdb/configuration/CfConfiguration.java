package fi.cdfdb.configuration;

import java.util.Optional;

public class CfConfiguration {

    private final static String UNKNOWN_VERSION_TAG = "UNKNOWN";

    public final String CLOWNFISH_VERSION = Optional.ofNullable(getClass().getPackage().getImplementationVersion())
            .orElse(UNKNOWN_VERSION_TAG);

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

    private static String resolveVersion(Class clazz) {
        Package packageInfo = clazz.getPackage();
        if(packageInfo == null) {
            return UNKNOWN_VERSION_TAG;
        }
        return Optional.ofNullable(packageInfo.getImplementationVersion()).orElse(UNKNOWN_VERSION_TAG) ;
    }
}
