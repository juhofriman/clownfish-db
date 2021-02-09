package fi.cdfdb.protocol;

import java.nio.charset.StandardCharsets;

public class CfQuery extends CfMessage<String> {

    public CfQuery(String payload) {
        super(payload);
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
        return MessageType.QUERY.type;
    }
}
