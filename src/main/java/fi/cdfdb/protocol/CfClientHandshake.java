package fi.cdfdb.protocol;

public class CfClientHandshake extends CfMessage {

    public final static String STATIC_PAYLOAD = "hellopingpong";

    public CfClientHandshake() {
        super(STATIC_PAYLOAD);
    }

    public CfClientHandshake(String received) {
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
