package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;
import fi.cdfdb.protocol.CfClientHandshake;
import fi.cdfdb.protocol.CfError;
import fi.cdfdb.protocol.CfMessage;
import fi.cdfdb.protocol.CfQuery;
import fi.cdfdb.protocol.exception.CfProtocolException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ServerSideClientWorker implements Runnable {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private final CfConfiguration serverConfiguration;
    private final SocketCloseSignal close;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final ServerSideProcessHandler serverSideProcessHandler;

    public ServerSideClientWorker(CfConfiguration serverConfiguration, SocketCloseSignal close, DataInputStream in, DataOutputStream out) {
        this.serverConfiguration = serverConfiguration;
        this.close = close;
        this.in = in;
        this.out = out;
        this.serverSideProcessHandler = new ServerSideProcessHandler(serverConfiguration);
    }

    @Override
    public void run() {
        LOG.info("Starting new dedicated thread for client");
        try {
            while (true) {

                try {
                    CfMessage cfMessage = readMessage();
                    out.write(this.serverSideProcessHandler.handle(cfMessage).serialize());
                } catch (CfProtocolException protocolException) {
                    CfError cfError = protocolException.errorToWire();
                    LOG.warning("Protocol exception: " + cfError.payload);
                    out.write(cfError.serialize());
                    continue;
                }
            }
        } catch (IOException exception) {
            if(exception instanceof EOFException) {
                LOG.info("Client hung up");
            } else if(exception instanceof SocketException) {
                LOG.info("Client hung up (socket exception)");
            } else {
                exception.printStackTrace();
            }
        }
        close.close();
    }

    private CfMessage readMessage() throws CfProtocolException {
        try {
            byte type = in.readByte();
            short length = in.readShort();
            String payload = readPayload(length);
            CfMessage.MessageType messageType = CfMessage.resolveMessageType(type);
            switch (messageType) {
                case CONNECTION:  return new CfClientHandshake(payload);
                case QUERY:  return new CfQuery(payload);
                default: throw new RuntimeException("Does not handle: " + messageType);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }

    }

    private String readPayload(short length) throws IOException {
        byte[] payload = new byte[length];
        in.readFully(payload);
        return new String(payload, StandardCharsets.UTF_8);
    }
}
