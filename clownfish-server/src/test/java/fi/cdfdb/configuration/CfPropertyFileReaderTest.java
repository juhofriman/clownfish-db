package fi.cdfdb.configuration;

import fi.cdfdb.testutil.TestConfigurationSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CfPropertyFileReaderTest implements TestConfigurationSupport {

    @Test
    void testReadingValues() {
        CfPropertyFileReader reader =
                new CfPropertyFileReader(resolveTestResourcePath("cf_test.properties"));

        assertEquals("9999", reader.readString(CfConfigurationKey.PORT));
        assertEquals(9999, reader.readInteger(CfConfigurationKey.PORT));
        assertEquals("localhost", reader.readString(CfConfigurationKey.BIND_ADDRESS));
        assertEquals("false", reader.readString(CfConfigurationKey.HEARTBEAT_ENABLED));
        assertFalse(reader.readBoolean(CfConfigurationKey.HEARTBEAT_ENABLED));
    }

    @Test
    void assertThrowsForInvalidValues() {
        CfPropertyFileReader reader =
                new CfPropertyFileReader(resolveTestResourcePath("cf_test.properties"));
        Assertions.assertThrows(CfInvalidConfigurationException.class, () -> {
            reader.readInteger(CfConfigurationKey.BIND_ADDRESS);
        });
        Assertions.assertThrows(CfInvalidConfigurationException.class, () -> {
            reader.readBoolean(CfConfigurationKey.BIND_ADDRESS);
        });
    }
}