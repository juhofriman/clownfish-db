package fi.cdfdb.configuration;

public interface ConfigurationValidator<T> {

    boolean validate(T value);

}
