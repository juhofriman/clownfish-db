package fi.cdfdb.configuration;

import fi.cdfdb.exception.UnrecoverableCFException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class CfPropertyFileReader extends CfDefaultConfigurationReader {

    private Properties props = new Properties();

    public CfPropertyFileReader(String filePath) {
        try {
            InputStream input = new FileInputStream(filePath);
            this.props.load(input);
            input.close();
        } catch (IOException exception) {
            throw new UnrecoverableCFException(String.format("Can't read properties file {%s}", filePath), exception);
        }
    }

    @Override
    public Optional<String> read(String name) {
        return Optional.of(this.props.getProperty(name));
    }
}
