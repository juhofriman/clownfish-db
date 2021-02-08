package fi.cdfdb.configuration;

public interface CfConfigurationReader {

    /**
     * Reads configuration for `CfConfigurationKey<T>` where T is
     * the runtime type of the value. All configuration are initially
     * read in as String and then mapped according to specification
     * in `CfConfigurationKey` static members.
     *
     * @param key key to resolve
     * @param <T> type of the returned value
     * @return resolved value for key
     */
    <T> T read(CfConfigurationKey<T> key);

}
