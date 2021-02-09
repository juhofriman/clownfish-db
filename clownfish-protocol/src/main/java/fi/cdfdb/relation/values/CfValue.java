package fi.cdfdb.relation.values;

import fi.cdfdb.relation.types.CfIntegerType;
import fi.cdfdb.relation.types.CfType;

public abstract class CfValue<T> {

    private T value;

    public CfValue(T value) {
        this.value = value;
    }

    public final boolean isOfType(CfType type) {
        return type.wrappedClass().equals(getClass());
    };
}
