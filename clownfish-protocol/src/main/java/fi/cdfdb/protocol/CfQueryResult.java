package fi.cdfdb.protocol;

import fi.cdfdb.relation.Relation;

import java.nio.charset.StandardCharsets;

public class CfQueryResult extends CfMessage<String> {

    public final static String STATIC_PAYLOAD = "thisisqueryresultkinds";

    public static CfQueryResult construct(Relation relation) {
        return null;
    }

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
    protected String deserialize(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    protected byte[] serialize(String payload) {
        return payload.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected byte idByte() {
        return MessageType.RELATION.type;
    }
}
