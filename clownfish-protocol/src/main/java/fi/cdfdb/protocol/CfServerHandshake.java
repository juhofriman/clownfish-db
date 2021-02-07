package fi.cdfdb.protocol;

public class CfServerHandshake extends CfMessage {

    public final static String STATIC_PAYLOAD = "itsmedingdong";

    public static CfServerHandshake construct(String version) {
        return new CfServerHandshake(STATIC_PAYLOAD + version);
    }

    public CfServerHandshake(String payload) {
        super(payload);
    }

    @Override
    protected byte idByte() {
        return MessageType.CONNECTION.type;
    }
}
