package fi.cdfdb.relation.types;

import fi.cdfdb.relation.values.CfInteger;
import fi.cdfdb.relation.values.CfValue;

public class CfIntegerType extends CfType {

    public CfIntegerType(String name) {
        super(name);
    }

    @Override
    public Class<? extends CfValue> wrappedClass() {
        return CfInteger.class;
    }

}
