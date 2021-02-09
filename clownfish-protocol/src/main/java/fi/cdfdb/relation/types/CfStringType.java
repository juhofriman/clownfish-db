package fi.cdfdb.relation.types;

import fi.cdfdb.relation.values.CfInteger;
import fi.cdfdb.relation.values.CfString;
import fi.cdfdb.relation.values.CfValue;

public class CfStringType extends CfType {

    public CfStringType(String name) {
        super(name);
    }

    @Override
    public Class<? extends CfValue> wrappedClass() {
        return CfString.class;
    }

}
