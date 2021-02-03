package fi.cdfdb.protocol;

public class CfQuery extends CfMessage {

    public CfQuery(String payload) {
        super(payload);
    }

    @Override
    protected byte idByte() {
        return 2;
    }
}
