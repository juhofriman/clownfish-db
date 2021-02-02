package fi.cdfdb.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface TestConfigurationSupport {
    default String resolveTestResourcePath(String cfgFile) {
        Path resourceDirectory = Paths.get("src","test", "resources", cfgFile);
        return resourceDirectory.toFile().getAbsolutePath();
    }
}
