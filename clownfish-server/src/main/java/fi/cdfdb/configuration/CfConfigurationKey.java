package fi.cdfdb.configuration;

import java.util.Optional;

public class CfConfigurationKey<T> {

    public static CfConfigurationKey<Integer> PORT = integerProperty(
            "cf.port",
            1234,
            Optional.empty());

    public static CfConfigurationKey<String> BIND_ADDRESS = stringProperty(
            "cf.bind_address",
            "127.0.0.1",
            Optional.empty());

    public static CfConfigurationKey<Boolean> HEARTBEAT_ENABLED = booleanProperty(
            "heartbeat.enabled",
            true);

    public static CfConfigurationKey<Integer> HEARTBEAT_INTERVAL_MS  = integerProperty(
            "heartbeat.interval",
            5000,
            Optional.empty());

    public static CfConfigurationKey<String> THREADPOOL_TYPE = stringProperty(
                    "threadpool.type",
                    "fixed",
                    Optional.of((value) -> {
                        if(value.equals("fixed") || value.equals("cached")) {
                            return Optional.empty();
                        }
                        return Optional.of("Must be 'fixed' or 'cached'");
                    }));

    private static CfConfigurationKey<Integer> integerProperty(
            String name,
            Integer defaultValue,
            Optional<ConfigurationValidator<Integer>> validator) {
        return new CfConfigurationKey(Integer::parseInt, name, defaultValue, validator);
    }

    private static CfConfigurationKey<String> stringProperty(
            String name,
            String defaultValue,
            Optional<ConfigurationValidator<String>> validator) {
        return new CfConfigurationKey((value) -> value, name, defaultValue, validator);
    }

    private static CfConfigurationKey<Boolean> booleanProperty(
            String name,
            Boolean defaultValue) {
        return new CfConfigurationKey(Boolean::valueOf, name, defaultValue, Optional.empty());
    }

    public final String name;
    public final Optional<T> defaultValue;
    public final ConfigurationValidator validator;
    public final ConfigurationMapper<T> mapper;

    private CfConfigurationKey(ConfigurationMapper<T> mapper, String name, T defaultValue, Optional<ConfigurationValidator<T>> validator) {
        this.name = name;
        this.defaultValue = Optional.of(defaultValue);
        this.validator = validator.orElse((value) -> Optional.empty());
        this.mapper = mapper;
    }

    public T valueOf(String readValue) {
        T mappedValue = this.mapper.map(readValue);
        Optional<String> validationError = this.validator.validate(mappedValue);
        if(validationError.isPresent()) {
            throw new CfInvalidConfigurationException(
                    this,
                    readValue,
                    validationError.get());
        }
        return mappedValue;
    }
}
