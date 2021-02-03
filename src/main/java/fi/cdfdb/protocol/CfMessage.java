package fi.cdfdb.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class CfMessage {

    public final String payload;

    public CfMessage(String payload) {
        this.payload = payload;
    }

    public static CfMessage read(byte[] message) {
        byte b = message[0];
        switch (b) {
            case 1: return new CfHandshake(readPayload(message));
        }
        return null;
    }

    private static String readPayload(byte[] message) {
        short aShort = ByteBuffer.wrap(message, 1, 2).order(ByteOrder.BIG_ENDIAN).getShort();
        return new String(ByteBuffer.allocate(aShort).put(message, 3, aShort).array(), StandardCharsets.UTF_8);
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
}
