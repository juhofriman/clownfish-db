package fi.cdfdb.configuration;

import fi.cdfdb.exception.UnrecoverableCFException;

public class CfInvalidConfigurationException extends UnrecoverableCFException {

    public CfInvalidConfigurationException(CfConfigurationKey key,
                                           String stringValue,
                                           String message) {
        super(formatMessage(key, stringValue, message));
    }

    public CfInvalidConfigurationException(CfConfigurationKey key,
                                           String stringValue,
                                           String message,
                                           Exception rootCause) {
        super(formatMessage(key, stringValue, message), rootCause);
    }

    private static String formatMessage(CfConfigurationKey key, String stringValue, String message) {
        return String.format("Invalid configuration value {%s=%s} %s", key.name, stringValue, message);
    }
}
