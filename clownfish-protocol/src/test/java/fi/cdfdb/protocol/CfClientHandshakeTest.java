package fi.cdfdb.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CfClientHandshakeTest {

    @Test
    void testHandshakeToBytesAndBack() {
        CfClientHandshake cfClientHandshake = new CfClientHandshake();
        CfMessage received = CfMessage.read(cfClientHandshake.serialize());
        assertEquals(cfClientHandshake.payload, received.payload);
    }
}