package fi.cdfdb.configuration;

public interface CfConfigurationReader {

    String readString(CfConfigurationKey port);
    Integer readInteger(CfConfigurationKey port);
}
