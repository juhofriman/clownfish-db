package fi.cdfdb.protocol;

public class CfQueryResult extends CfMessage {

    public final static String STATIC_PAYLOAD = "thisisqueryresultkinds";

    public CfQueryResult() {
        super(STATIC_PAYLOAD);
    }

    public CfQueryResult(String received) {
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
