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

        assertEquals("9999", reader.readString(CfConfigurationKey.PORT.name).get());
        assertEquals("9999", reader.readString(CfConfigurationKey.PORT.name).get());
        assertEquals("localhost", reader.readString(CfConfigurationKey.BIND_ADDRESS.name).get());
        assertEquals("false", reader.readString(CfConfigurationKey.HEARTBEAT_ENABLED.name).get());
        assertEquals("false", reader.readString(CfConfigurationKey.HEARTBEAT_ENABLED.name).get());
    }

}