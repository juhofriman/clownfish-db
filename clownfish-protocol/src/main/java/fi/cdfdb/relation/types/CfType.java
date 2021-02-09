package fi.cdfdb.relation.types;

import fi.cdfdb.relation.values.CfValue;

public abstract class CfType {

    private String name;

    public CfType(String name) {
        this.name = name;
    }

    public abstract Class<? extends CfValue> wrappedClass();
}
