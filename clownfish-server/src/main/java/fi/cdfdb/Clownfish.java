package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;
import fi.cdfdb.configuration.CfDefaultConfigurationReader;
import fi.cdfdb.configuration.CfPropertyFileReader;

import java.io.File;
import java.util.Optional;

public class Clownfish {

    public static void main(String[] args) {
        ClownfishServer clownFish = new ClownfishServer(new CfConfiguration(getReader()));
        clownFish.run();
    }

    private static CfDefaultConfigurationReader getReader() {
        String HARD_CONFIG_FILE = "cf-config.properties";
        if(new File(HARD_CONFIG_FILE).exists()) {
            return new CfPropertyFileReader(HARD_CONFIG_FILE);
        }
        return new CfDefaultConfigurationReader() {
            @Override
            public Optional<String> readString(String name) {
                return Optional.empty();
            }
        };
    }
}
