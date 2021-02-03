package fi.cdfdb.protocol;

public class CfHandshake extends CfMessage {

    private final static String STATIC_PAYLOAD = "hellopingpong";

    public CfHandshake() {
        super(STATIC_PAYLOAD);
    }

    public CfHandshake(String received) {
        super(STATIC_PAYLOAD);
        if(!received.equals(STATIC_PAYLOAD)) {
            throw new RuntimeException("Got " + received);
        }
    }

    @Override
    protected byte idByte() {
        return 1;
    }
}
