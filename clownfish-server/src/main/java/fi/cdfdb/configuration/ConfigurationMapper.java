package fi.cdfdb.configuration;

public interface ConfigurationMapper<T> {

    /**
     * Maps value of String to value of T
     *
     * @param value
     * @return
     */
    T map(String value);

}
