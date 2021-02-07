package fi.cdfdb.protocol;

public class CfServerHandshake extends CfMessage {

    public final static String STATIC_PAYLOAD = "itsmedingdong";

    public CfServerHandshake() {
        super(STATIC_PAYLOAD);
    }

    public CfServerHandshake(String received) {
        super(STATIC_PAYLOAD);
        if(!received.equals(STATIC_PAYLOAD)) {
            throw new RuntimeException("Got " + received);
        }
    }

    @Override
    protected byte idByte() {
        return MessageType.CONNECTION.type;
    }
}
