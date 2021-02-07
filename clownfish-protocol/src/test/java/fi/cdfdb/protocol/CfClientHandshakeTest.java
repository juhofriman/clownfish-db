package fi.cdfdb.protocol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CfClientHandshakeTest {

    @Test
    void testHandshakeToBytesAndBack() {
        CfClientHandshake cfClientHandshake = CfClientHandshake.construct("1.0.0");
        CfMessage received = CfMessage.read(cfClientHandshake.serialize());
        assertEquals(cfClientHandshake.payload, received.payload);
        assertEquals("1.0.0", ((CfClientHandshake) received).getVersion());
    }
}