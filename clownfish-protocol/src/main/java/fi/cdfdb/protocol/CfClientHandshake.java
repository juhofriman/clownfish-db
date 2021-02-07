package fi.cdfdb.protocol;

public class CfClientHandshake extends CfMessage {

    public final static String STATIC_PAYLOAD = "hellopingpong";

    public static CfClientHandshake construct(String version) {
        return new CfClientHandshake(STATIC_PAYLOAD + version);
    }

    public CfClientHandshake(String payload) {
        super(payload);
    }

    @Override
    protected byte idByte() {
        return MessageType.CONNECTION.type;
    }

    public String getVersion() {
        return payload.substring(STATIC_PAYLOAD.length());
    }
}
