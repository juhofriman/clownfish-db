package fi.cdfdb.protocol;

public class CfError extends CfMessage {
    public CfError(String payload) {
        super(payload);
    }

    @Override
    protected byte idByte() {
        return MessageType.ERROR.type;
    }
}
