package fi.cdfdb.protocol;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;

class CfClientHandshakeTest extends SerializationTestSupport {

    @Test
    void testHandshakeToBytesAndBack() {
        CfClientHandshake cfClientHandshake = new CfClientHandshake("0.0.1");
        assertEquals(CfMessage.MessageType.CONNECTION.type, cfClientHandshake.idByte());
        byte[] bytesInWire = cfClientHandshake.serialize();

        byte type = bytesInWire[0];
        short length = getLength(bytesInWire);

        assertEquals(CfMessage.MessageType.CONNECTION.type, type);
        assertEquals(cfClientHandshake.getPayloadData().length(), length); // This is just known...
        CfClientHandshake deserialized = new CfClientHandshake(
                allocatePayload(bytesInWire, length));

        assertEquals(cfClientHandshake.getPayloadData(), deserialized.getPayloadData());
    }

    @Test
    void assertCatchesInvalidBytes() {
        BogusStringMessage bogusStringMessage = new BogusStringMessage("this-is-not-valid");
        assertThrows(RuntimeException.class, () -> new CfClientHandshake(allocatePayload(bogusStringMessage.serialize(), (short) 5)));
    }
}