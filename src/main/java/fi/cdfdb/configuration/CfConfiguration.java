package fi.cdfdb.configuration;

import java.util.Optional;

public class CfConfiguration {

    public final String BIND_ADDRESS;
    public final Integer PORT;

    public CfConfiguration(CfConfigurationReader reader) {
        this.BIND_ADDRESS = reader.readString(CfConfigurationKey.BIND_ADDRESS);
        this.PORT = reader.readInteger(CfConfigurationKey.PORT);
    }
}
