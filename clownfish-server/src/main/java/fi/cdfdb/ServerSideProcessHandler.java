package fi.cdfdb;

import fi.cdfdb.protocol.*;

import java.util.logging.Logger;

public class ServerSideProcessHandler {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private boolean handshakeDone = false;

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
        handshakeDone = true;
        return new CfServerHandshake();
    }

    public CfMessage handleQuery(CfQuery cfMessage) {
        if(!handshakeDone) {
            LOG.warning("Received query, but handshake is not done");
            return new CfError(CfError.ERROR_CODE.PENDING_HANDSHAKE);
        }
        LOG.info(String.format("Querying %s", cfMessage.payload));
        return new CfQueryResult();
    }

}
