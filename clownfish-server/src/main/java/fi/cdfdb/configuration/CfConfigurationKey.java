package fi.cdfdb.configuration;

import java.util.Optional;

public class CfConfigurationKey<T> {

    /* Configuration entries */

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

    public static CfConfigurationKey<Integer> FIXED_THREADPOOL_SIZE = integerProperty(
            "threadpool.fixed.size",
            10,
            Optional.empty()
    );

    /* Utils for easily creating typesafe configurations */

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

    /** Name of this key, interpreted as configuration key name  */
    public final String name;
    /** Default value can be optional but at this point we don't have configuration that can be missing */
    public final T defaultValue;
    /** Validator for resolved value */
    public final ConfigurationValidator validator;
    /** Mapper, which takes String and maps it to T */
    public final ConfigurationMapper<T> mapper;

    /**
     * Creates configuration key entry.
     *
     * @param mapper
     * @param name
     * @param defaultValue
     * @param validator
     */
    private CfConfigurationKey(
            ConfigurationMapper<T> mapper,
            String name,
            T defaultValue,
            Optional<ConfigurationValidator<T>> validator) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.validator = validator.orElse((value) -> Optional.empty());
        this.mapper = mapper;
    }

    /**
     * Maps and validated value read.
     *
     * @param readValue as String
     * @throws CfInvalidConfigurationException when received value is not valid. Throwing this should
     *  halt the starting of server as it is UnrecoverableCFException
     * @return value mapped to T
     */
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
