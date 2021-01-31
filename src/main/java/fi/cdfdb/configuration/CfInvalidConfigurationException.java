package fi.cdfdb.configuration;

import fi.cdfdb.exception.UnrecoverableCFException;

public class CfInvalidConfigurationException extends UnrecoverableCFException {
    public CfInvalidConfigurationException(CfConfigurationKey key, Exception rootCause) {
        super(String.format("Invalid configuration value {%s}", key.name), rootCause);
    }
}
