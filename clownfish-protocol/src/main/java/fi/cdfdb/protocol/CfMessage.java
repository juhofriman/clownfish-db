package fi.cdfdb.protocol;

import fi.cdfdb.protocol.exception.CfProtocolException;
import fi.cdfdb.protocol.exception.UnknownMessageTypeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public abstract class CfMessage<T> {

    public enum MessageType {

        CONNECTION((byte) 1),
        META((byte) 2),
        QUERY((byte) 3),
        RELATION((byte) 5),
        ERROR((byte) 4);

        public final byte type;

        MessageType(byte type) {
            this.type = type;
        }
    }

    /** Contained message date */
    private final T payloadData;

    /**
     * Creates message with runtime value to be serialized to the wire.
     *
     * @param payload
     */
    public CfMessage(T payload) {
        this.payloadData = payload;
    }

    /**
     * Deserialise and optionally validate received bytes.
     *
     * Caution: byte array MUST be the payload part and type and length must
     * be omitted.
     *
     * @param bytes
     */
    public CfMessage(byte[] bytes) {
        this.payloadData = this.deserialize(bytes);
        // It's a good idea, but need some thinking on the calling end
        Optional<String> validationError = this.validate(this.payloadData);
        if(validationError.isPresent()) {
            throw new RuntimeException(validationError.get());
        }
    }

    public T getPayloadData() {
        return payloadData;
    }

    /**
     * Implementing class must tell which byte denotes this message.
     *
     * @return
     */
    protected abstract byte idByte();

    /**
     * Tells how message is to be serialized to the wire.
     *
     * @param payload
     * @return
     */
    protected abstract byte[] serialize(T payload);

    /**
     * Tells how message from the wire is deserialized.
     *
     * @param bytes
     * @return
     */
    protected abstract T deserialize(byte[] bytes);

    /**
     * Optional validation method for deserialized message.
     *
     * @param payloadData
     * @return
     */
    protected Optional<String> validate(T payloadData) {
        return Optional.empty();
    }

    /**
     * Serialized message to the wire. Adds message type byte and calculates
     * length.
     *
     * @return
     */
    public final byte[] serialize() {
        byte[] payload = this.serialize(this.payloadData);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(idByte());
        try {
            baos.write(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short) payload.length).array());
            baos.write(payload);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Something weird happened", exception);
        }
        return baos.toByteArray();
    }

    public static MessageType resolveMessageType(byte receivedByte) throws CfProtocolException {
        switch (receivedByte) {
            case (byte) 1: return MessageType.CONNECTION;
            case (byte) 2: return MessageType.META;
            case (byte) 3: return MessageType.QUERY;
            case (byte) 4: return MessageType.ERROR;
            default: throw new UnknownMessageTypeException(receivedByte);
        }
    }

}
