package fi.cdfdb.relation;

import fi.cdfdb.relation.exception.CfRelationException;
import fi.cdfdb.relation.types.CfIntegerType;
import fi.cdfdb.relation.types.CfStringType;
import fi.cdfdb.relation.values.CfString;
import fi.cdfdb.relation.values.CfInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationTest {

    @Test
    void assertCantBuildEmptyRelation() {
        assertThrows(CfRelationException.class, () -> new Relation());
    }

    @Test
    void testAddingRowsForSingleColumn() {
        Relation relation = new Relation(new CfIntegerType("foo"));
        relation.addRow(new CfInteger(1));
        assertEquals(1, relation.rows().size());
        relation.addRow(new CfInteger(1));
        assertEquals(2, relation.rows().size());
    }

    @Test
    void testAddingRowsForMultiColumn() {
        Relation relation = new Relation(new CfIntegerType("foo"), new CfStringType("bar"));
        relation.addRow(new CfInteger(1), new CfString("hello"));
        assertEquals(1, relation.rows().size());
        relation.addRow(new CfInteger(1), new CfString("world"));
        assertEquals(2, relation.rows().size());
    }

    @Test
    void assertCantAddEmptyRowToRelation() {
        assertThrows(CfRelationException.class, () -> {
            Relation relation = new Relation(new CfIntegerType("foo"));
            relation.addRow();
        });
    }

    @Test
    void assertCantAddInvalidTypeToRelation() {
        assertThrows(CfRelationException.class, () -> {
            Relation relation = new Relation(new CfIntegerType("foo"));
            relation.addRow(new CfString("hello - I should be integer"));
        });

        assertThrows(CfRelationException.class, () -> {
            Relation relation = new Relation(new CfStringType("foo"));
            relation.addRow(new CfInteger(1));
        });
    }

    @Test
    void assertCantAddTooManyColumnsToRelation() {
        assertThrows(CfRelationException.class, () -> {
            Relation relation = new Relation(new CfIntegerType("foo"));
            relation.addRow(new CfInteger(1), new CfInteger(2));
        });
    }

    @Test
    void assertCantAddTooFewColumnsToRelation() {
        assertThrows(CfRelationException.class, () -> {
            Relation relation = new Relation(new CfIntegerType("foo"), new CfIntegerType("bar"));
            relation.addRow(new CfInteger(1));
        });
    }
}