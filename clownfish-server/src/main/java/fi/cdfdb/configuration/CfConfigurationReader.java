package fi.cdfdb.configuration;

public interface CfConfigurationReader {

    <T> T read(CfConfigurationKey<T> key);
    
}
