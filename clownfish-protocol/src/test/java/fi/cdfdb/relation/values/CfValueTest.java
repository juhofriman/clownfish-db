package fi.cdfdb.relation.values;

import fi.cdfdb.relation.types.CfIntegerType;
import fi.cdfdb.relation.types.CfStringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CfValueTest {

    @Test
    void testIntegerValue() {
        CfInteger integer = new CfInteger(1);
        assertTrue(integer.isOfType(new CfIntegerType("foo")));
        assertFalse(integer.isOfType(new CfStringType("foo")));
    }

    @Test
    void testStringValue() {
        CfString string = new CfString("foo");
        assertFalse(string.isOfType(new CfIntegerType("foo")));
        assertTrue(string.isOfType(new CfStringType("foo")));
    }
}