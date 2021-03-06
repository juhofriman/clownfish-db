package fi.cdfdb.configuration;

import fi.cdfdb.exception.UnrecoverableCFException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class CfPropertyFileReader extends CfDefaultConfigurationReader {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private final Properties props = new Properties();

    public CfPropertyFileReader(String filePath) {
        LOG.info(String.format("Reading properties from %s", filePath));
        try {
            InputStream input = new FileInputStream(filePath);
            this.props.load(input);
            input.close();
        } catch (IOException exception) {
            throw new UnrecoverableCFException(String.format("Can't read properties file {%s}", filePath), exception);
        }
    }

    @Override
    public Optional<String> readString(String name) {
        return Optional.ofNullable(this.props.getProperty(name));
    }
}
