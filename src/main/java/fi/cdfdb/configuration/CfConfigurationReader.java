package fi.cdfdb.configuration;

public interface CfConfigurationReader {

    /**
     * Read configuration key value as a string
     *
     * @param key key definition
     * @return
     */
    String readString(CfConfigurationKey key);

    /**
     * Read configuration key value as an integer
     *
     * @param key
     * @return
     */
    Integer readInteger(CfConfigurationKey key);

    /**
     * Read configuration key value as a boolean
     *
     * @param key
     * @return
     */
    Boolean readBoolean(CfConfigurationKey key);
}
