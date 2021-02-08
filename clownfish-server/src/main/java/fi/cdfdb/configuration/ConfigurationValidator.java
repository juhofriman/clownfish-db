package fi.cdfdb.configuration;

import java.util.Optional;

public interface ConfigurationValidator<T> {

    /**
     * Validates given value. Should return `Optional.empty()` if given `T value`
     * is valid for this key.
     *
     * @param value of T to be validated
     * @return Optional.empty() if T values is valid
     */
    Optional<String> validate(T value);

}
