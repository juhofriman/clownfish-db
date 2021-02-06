package fi.cdfdb.protocol.exception;

import fi.cdfdb.protocol.CfError;

public abstract class CfProtocolException extends Throwable {
    public CfProtocolException(String message) {
        super(message);
    }

    public abstract CfError errorToWire();
}
