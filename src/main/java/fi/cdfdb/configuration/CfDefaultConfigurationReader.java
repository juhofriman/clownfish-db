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
        String stringValue = this.readString(key);
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException exception) {
            throw new CfInvalidConfigurationException(key, stringValue, "Expecting integer", exception);
        }
    }

    @Override
    public final Boolean readBoolean(CfConfigurationKey key) {
        String stringValue = this.readString(key);
        if(stringValue.equals("true")) {
            return true;
        }
        if(stringValue.equals("false")) {
            return false;
        }
        throw new CfInvalidConfigurationException(key, stringValue, "Expecting boolean");
    }

    /**
     * Reader must return prop for given name in String
     *
     * @param name
     * @return
     */
    public abstract Optional<String> read(String name);
}
