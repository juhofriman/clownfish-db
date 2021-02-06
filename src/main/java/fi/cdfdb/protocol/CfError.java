package fi.cdfdb.protocol;

public class CfError extends CfMessage {

    public enum ERROR_CODE {
        UNKNOWN_START_BYTE("CFERR-1", "Received unknown starting byte"),
        PENDING_HANDSHAKE("CFERR-2", "Please send handhshake before querying");

        private final String code;
        private final String defaultMessage;

        ERROR_CODE(String code, String defaultMessage) {
            this.code = code;
            this.defaultMessage = defaultMessage;
        }
    }

    public CfError(ERROR_CODE error) {
        super(error.code + ": " + error.defaultMessage);
    }

    @Override
    protected byte idByte() {
        return MessageType.ERROR.type;
    }
}
