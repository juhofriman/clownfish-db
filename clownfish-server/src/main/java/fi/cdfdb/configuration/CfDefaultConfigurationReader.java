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
        return key.defaultValue.orElseThrow(() -> new CfMissingConfigurationException(key));
    }

    public abstract Optional<String> readString(String name);

}
