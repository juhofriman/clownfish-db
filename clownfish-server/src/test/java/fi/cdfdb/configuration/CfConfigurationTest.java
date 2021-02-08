package fi.cdfdb.configuration;

import fi.cdfdb.testutil.TestConfigurationSupport;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CfConfigurationTest implements TestConfigurationSupport {

    @Test
    void testReadingAndFetchingDefaultValues() {
        CfConfiguration config = new CfConfiguration(new CfDefaultConfigurationReader() {
            @Override
            public Optional<String> readString(String name) {
                return Optional.empty();
            }
        });

        assertEquals(CfConfigurationKey.PORT.defaultValue.get(), config.PORT);
        assertEquals(CfConfigurationKey.BIND_ADDRESS.defaultValue.get(), config.BIND_ADDRESS);
        assertTrue(config.HEARTBEAT_ENABLED);
        assertEquals(CfConfigurationKey.HEARTBEAT_INTERVAL_MS.defaultValue.get(), config.HEARTBEAT_INTERVAL_MS);
        assertEquals(CfConfigurationKey.THREADPOOL_TYPE.defaultValue.get(), config.THREAD_POOL_TYPE);
    }

    @Test
    void testReadingAndFetchingPropertiesFromFile() {
        CfConfiguration config =
                new CfConfiguration(new CfPropertyFileReader(
                        resolveTestResourcePath("cf_test.properties")));

        assertEquals(9999, config.PORT);
        assertEquals("localhost", config.BIND_ADDRESS);
        assertFalse(config.HEARTBEAT_ENABLED);
        assertEquals(10, config.HEARTBEAT_INTERVAL_MS);
    }

    @Test
    void testReadingAndFetchingPropertiesPartiallyFromFile() {
        CfConfiguration config =
                new CfConfiguration(new CfPropertyFileReader(
                        resolveTestResourcePath("partial_cf_test.properties")));

        assertEquals(1234, config.PORT);
        assertEquals("127.0.0.1", config.BIND_ADDRESS);
        assertTrue(config.HEARTBEAT_ENABLED);
        assertEquals(10, config.HEARTBEAT_INTERVAL_MS);
    }

}
