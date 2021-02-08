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

    public final String THREAD_POOL_TYPE;
    public final Integer FIXED_THREAD_POOL_SIZE;

    public CfConfiguration(CfConfigurationReader reader) {
        this.BIND_ADDRESS = reader.read(CfConfigurationKey.BIND_ADDRESS);
        this.PORT = reader.read(CfConfigurationKey.PORT);
        this.HEARTBEAT_ENABLED = reader.read(CfConfigurationKey.HEARTBEAT_ENABLED);
        this.HEARTBEAT_INTERVAL_MS = reader.read(CfConfigurationKey.HEARTBEAT_INTERVAL_MS);
        this.THREAD_POOL_TYPE = reader.read(CfConfigurationKey.THREADPOOL_TYPE);
        this.FIXED_THREAD_POOL_SIZE = reader.read(CfConfigurationKey.FIXED_THREADPOOL_SIZE);
    }
}
