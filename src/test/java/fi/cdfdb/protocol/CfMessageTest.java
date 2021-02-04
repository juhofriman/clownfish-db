package fi.cdfdb.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CfMessageTest {

    @Test
    void testMessageIdBytes() {
        assertMessageType(CfMessage.MessageType.CONNECTION, (byte) 1);
        assertMessageType(CfMessage.MessageType.META, (byte) 2);
        assertMessageType(CfMessage.MessageType.QUERY, (byte) 3);
        assertMessageType(CfMessage.MessageType.ERROR, (byte) 4);
    }

    @Test
    void testThrowsForUnknownBytes() {
        assertThrows(CfProtocolException.class, () -> CfMessage.resolveMessageType((byte) 128));
    }

    private void assertMessageType(CfMessage.MessageType expectedType, byte receivedByte) {
        try {
            CfMessage.MessageType messageType = CfMessage.resolveMessageType(receivedByte);
            assertEquals(expectedType, messageType);
            assertEquals(expectedType.type, receivedByte);
        } catch (CfProtocolException e) {
            fail("Unexpected checked exception for receivedByte", e);
        }
    }
}