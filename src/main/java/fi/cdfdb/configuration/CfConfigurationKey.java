package fi.cdfdb.configuration;

import java.util.Optional;

public enum CfConfigurationKey {

    PORT("cf.port", "1234"),
    BIND_ADDRESS("cf.bind_address", "127.0.0.1");

    public final String name;
    public final Optional<String> defaultValue;

    CfConfigurationKey(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = Optional.of(defaultValue);
    }
}
