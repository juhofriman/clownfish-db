package fi.cdfdb.relation;

import fi.cdfdb.relation.exception.CfRelationException;
import fi.cdfdb.relation.types.CfType;
import fi.cdfdb.relation.values.CfValue;

import java.util.ArrayList;
import java.util.Collection;

public class Relation {

    private CfType[] columns;
    private ArrayList<CfValue[]> rows = new ArrayList<>();

    public Relation(CfType... columns) {
        if(columns.length == 0) {
            throw new CfRelationException("Can't build empty relation");
        }
        this.columns = columns;
    }

    public void addRow(CfValue... values) {
        if(values.length == 0) {
            throw new CfRelationException("Can't add empty row to relation");
        }

        if(values.length != columns.length) {
            throw new CfRelationException(
                    String.format("Adding row that do not match. Got %d columns, but expecting %d",
                            values.length,
                            columns.length));
        }

        for(int i = 0; i < this.columns.length; i++) {
            if(!values[i].isOfType(columns[i])) {
                throw new CfRelationException(String.format("Trying to add %s to index %d when %s is expected",
                        values[i].getClass().getName(),
                        i,
                        columns[i].wrappedClass().getName()));
            }
        }

        this.rows.add(values);
    }

    public Collection<CfValue[]> rows() {
        return this.rows;
    }
}
