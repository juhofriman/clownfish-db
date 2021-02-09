package fi.cdfdb.protocol;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class CfServerHandshake extends CfMessage<String> {

    public final static String HELLO_MESSAGE = "clownfish-server:";

    public CfServerHandshake(String version) {
        super(HELLO_MESSAGE + version);
    }

    public CfServerHandshake(byte[] bytes) {
        super(bytes);
    }

    @Override
    protected byte[] serialize(String payload) {
        return payload.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected String deserialize(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    protected Optional<String> validate(String payloadData) {
        if(payloadData.startsWith(HELLO_MESSAGE)) {
            return Optional.empty();
        }
        return Optional.of(String.format("Invalid handshake received %s", payloadData));
    }

    @Override
    protected byte idByte() {
        return MessageType.CONNECTION.type;
    }
}
