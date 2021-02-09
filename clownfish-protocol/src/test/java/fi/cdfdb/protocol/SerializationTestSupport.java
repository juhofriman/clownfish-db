package fi.cdfdb.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class SerializationTestSupport {

    protected short getLength(byte[] bytes) {
        return ByteBuffer.wrap(bytes, 1, 2)
                .order(ByteOrder.BIG_ENDIAN).getShort();
    }

    protected byte[] allocatePayload(byte[] bytes, short length) {
        return ByteBuffer.allocate(length).put(bytes, 3, length).array();
    }

    protected static class BogusStringMessage extends CfMessage<String> {

        public BogusStringMessage(String payload) {
            super(payload);
        }

        @Override
        protected byte idByte() {
            return 3;
        }

        @Override
        protected byte[] serialize(String payload) {
            return payload.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        protected String deserialize(byte[] bytes) {
            return new String(bytes, StandardCharsets.UTF_8);
        }
    }
}
