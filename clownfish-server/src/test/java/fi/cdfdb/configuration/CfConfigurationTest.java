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

        assertEquals(CfConfigurationKey.PORT.defaultValue, config.PORT);
        assertEquals(CfConfigurationKey.BIND_ADDRESS.defaultValue, config.BIND_ADDRESS);
        assertTrue(config.HEARTBEAT_ENABLED);
        assertEquals(CfConfigurationKey.HEARTBEAT_INTERVAL_MS.defaultValue, config.HEARTBEAT_INTERVAL_MS);
        assertEquals(CfConfigurationKey.THREADPOOL_TYPE.defaultValue, config.THREAD_POOL_TYPE);
        assertEquals(CfConfigurationKey.FIXED_THREADPOOL_SIZE.defaultValue, config.FIXED_THREAD_POOL_SIZE);
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
    void assertThrowsForInvalidValue() {
        assertThrows(CfInvalidConfigurationException.class, () -> new CfConfiguration(new CfDefaultConfigurationReader() {
            @Override
            public Optional<String> readString(String name) {
                if(name.equals(CfConfigurationKey.THREADPOOL_TYPE.name)) {
                    return Optional.of("this_is_not_thread_pool_value");
                }
                return Optional.empty();
            }
        }));
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
