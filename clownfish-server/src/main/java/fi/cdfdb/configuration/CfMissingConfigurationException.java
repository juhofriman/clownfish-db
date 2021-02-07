package fi.cdfdb.configuration;

import fi.cdfdb.exception.UnrecoverableCFException;

public class CfMissingConfigurationException extends UnrecoverableCFException {
    public CfMissingConfigurationException(CfConfigurationKey key) {
        super(String.format("Missing configuration value {%s}", key.name));
    }
}
