package fi.cdfdb.protocol.exception;

import fi.cdfdb.protocol.CfError;

public class UnknownMessageTypeException extends CfProtocolException {
    public UnknownMessageTypeException(byte receivedByte) {
        super(String.format("Unkown starting byte=%s received from the wire", receivedByte));
    }

    @Override
    public CfError errorToWire() {
        return new CfError(CfError.ERROR_CODE.UNKNOWN_START_BYTE);
    }
}
