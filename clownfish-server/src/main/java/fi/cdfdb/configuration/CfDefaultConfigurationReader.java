package fi.cdfdb.configuration;

import java.util.Optional;

/**
 * Configuration reader that are fallbacks to default values in `CfConfigurationKey`
 */
public abstract class CfDefaultConfigurationReader implements CfConfigurationReader {

    public <T> T read(CfConfigurationKey<T> key) {
        Optional<String> valueFromConcreteReader = this.readString(key.name);
        if(valueFromConcreteReader.isPresent()) {
            return key.valueOf(valueFromConcreteReader.get());
        }
        // Each configuration entry has default value
        return key.defaultValue;
    }

    /**
     * Implementing class must be able to return value for configuration name.
     *
     * Return Optional.empty() if the key is not available in configuration and
     * then the default value will be used.
     *
     * @param name of the resolved configuration key
     * @return value for key or Optional.empty() if no value for key exists
     */
    public abstract Optional<String> readString(String name);

}
