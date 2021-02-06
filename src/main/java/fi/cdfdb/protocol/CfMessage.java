package fi.cdfdb.protocol;

import fi.cdfdb.protocol.exception.CfProtocolException;
import fi.cdfdb.protocol.exception.UnknownMessageTypeException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class CfMessage {

    public enum MessageType {

        CONNECTION((byte) 1),
        META((byte) 2),
        QUERY((byte) 3),
        ERROR((byte) 4);

        public final byte type;

        MessageType(byte type) {
            this.type = type;
        }
    }

    public final String payload;

    public CfMessage(String payload) {
        this.payload = payload;
    }

    public byte[] serialize() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(idByte());
        try {
            byte[] payloadBytes = payload.getBytes(StandardCharsets.UTF_8);
            baos.write(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short) payloadBytes.length).array());
            baos.write(payloadBytes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return baos.toByteArray();
    }

    protected abstract byte idByte();

    public static CfMessage read(byte[] message) {
        byte b = message[0];
        switch (b) {
            case 1: return new CfClientHandshake(readPayload(message));
        }
        return null;
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

    private static String readPayload(byte[] message) {
        short aShort = ByteBuffer.wrap(message, 1, 2).order(ByteOrder.BIG_ENDIAN).getShort();
        return new String(ByteBuffer.allocate(aShort).put(message, 3, aShort).array(), StandardCharsets.UTF_8);
    }
}
