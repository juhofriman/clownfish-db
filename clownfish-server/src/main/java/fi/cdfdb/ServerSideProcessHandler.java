package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;
import fi.cdfdb.protocol.*;
import fi.cdfdb.relation.Relation;
import fi.cdfdb.relation.types.CfIntegerType;
import fi.cdfdb.relation.types.CfStringType;

import java.util.logging.Logger;

public class ServerSideProcessHandler {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private boolean handshakeDone = false;
    private final CfConfiguration serverConfiguration;

    public ServerSideProcessHandler(CfConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

    public CfMessage handle(CfMessage cfMessage) {
        if(cfMessage instanceof CfClientHandshake) {
            return handleHandshake((CfClientHandshake) cfMessage);
        }

        if(cfMessage instanceof CfQuery) {
            return handleQuery((CfQuery) cfMessage);
        }

        throw new RuntimeException(String.format("Can't handle %s", cfMessage.getClass()));
    }

    public CfMessage handleHandshake(CfClientHandshake cfMessage) {
        LOG.info(String.format("Received handshake with client version %s", cfMessage.getVersion()));
        handshakeDone = true;
        return new CfServerHandshake(serverConfiguration.CLOWNFISH_VERSION);
    }

    public CfMessage handleQuery(CfQuery cfMessage) {
        if(!handshakeDone) {
            LOG.warning("Received query, but handshake is not done");
            return new CfError(CfError.ERROR_CODE.PENDING_HANDSHAKE);
        }
        LOG.info(String.format("Querying %s", cfMessage.getPayloadData()));

        // XXX: This could be "BackendRelation" or similar?
        Relation relation = new Relation(new CfIntegerType("foo"), new CfStringType("bar"));
        return null;
    }

}
