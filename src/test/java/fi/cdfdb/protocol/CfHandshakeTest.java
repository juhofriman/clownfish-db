package fi.cdfdb.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CfHandshakeTest {

    @Test
    void testHandshakeToBytesAndBack() {
        CfHandshake cfHandshake = new CfHandshake();
        CfMessage received = CfMessage.read(cfHandshake.serialize());
        assertEquals(cfHandshake.payload, received.payload);
    }
}