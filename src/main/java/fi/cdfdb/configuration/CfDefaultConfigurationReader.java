package fi.cdfdb.configuration;

import java.util.Optional;

/**
 * Configuration reader that are fallbacks to default values in `CfConfigurationKey`
 */
public abstract class CfDefaultConfigurationReader implements CfConfigurationReader {

    @Override
    public final String readString(CfConfigurationKey key) {
        Optional<String> valueFromReader = this.read(key.name);
        if(valueFromReader.isPresent()) {
            return valueFromReader.get();
        }
        if(key.defaultValue.isPresent()) {
            return key.defaultValue.get();
        }
        throw new CfMissingConfigurationException(key);
    }

    @Override
    public final Integer readInteger(CfConfigurationKey key) {
        try {
            return Integer.parseInt(this.readString(key));
        } catch (NumberFormatException exception) {
            throw new CfInvalidConfigurationException(key, exception);
        }
    }

    /**
     * Reader must return prop for given name in String
     *
     * @param name
     * @return
     */
    public abstract Optional<String> read(String name);
}
