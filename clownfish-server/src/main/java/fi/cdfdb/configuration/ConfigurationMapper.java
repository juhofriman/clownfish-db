package fi.cdfdb.configuration;

public interface ConfigurationMapper<T> {

    T map(String value);

}
