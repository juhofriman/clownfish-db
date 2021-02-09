package fi.cdfdb.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CfServerHandshakeTest extends SerializationTestSupport {

    @Test
    void testHandshakeToBytesAndBack() {
        CfServerHandshake cfClientHandshake = new CfServerHandshake("0.0.1");
        assertEquals(CfMessage.MessageType.CONNECTION.type, cfClientHandshake.idByte());
        byte[] bytesInWire = cfClientHandshake.serialize();

        byte type = bytesInWire[0];
        short length = getLength(bytesInWire);

        assertEquals(CfMessage.MessageType.CONNECTION.type, type);
        assertEquals(cfClientHandshake.getPayloadData().length(), length); // This is just known...
        CfServerHandshake deserialized = new CfServerHandshake(
                allocatePayload(bytesInWire, length));

        assertEquals(cfClientHandshake.getPayloadData(), deserialized.getPayloadData());
    }

    @Test
    void assertCatchesInvalidBytes() {
        BogusStringMessage bogusStringMessage = new BogusStringMessage("this-is-not-valid");
        assertThrows(RuntimeException.class, () -> new CfServerHandshake(allocatePayload(bogusStringMessage.serialize(), (short) 5)));
    }
}