package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;
import fi.cdfdb.configuration.CfDefaultConfigurationReader;

import java.util.Optional;

public class Clownfish {

    public static void main(String[] args) {
        ClownfishServer clownFish = new ClownfishServer(new CfConfiguration(new CfDefaultConfigurationReader() {
            @Override
            public Optional<String> read(String name) {
                return Optional.empty();
            }
        }));
        clownFish.run();
    }
}
