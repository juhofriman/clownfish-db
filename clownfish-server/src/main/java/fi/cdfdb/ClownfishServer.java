package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;
import fi.cdfdb.exception.UnrecoverableCFException;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ClownfishServer implements Runnable {

    static {
        InputStream stream = ClownfishServer.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Logger LOG = Logger.getLogger(getClass().getName());

    /** Runtime configuration of the clownfish server */
    // TODO needs to be final
    private CfConfiguration serverConfiguration;

    /** Server socket instance of Clownfish */
    private final ServerSocket socket;

    /** Connection manager for handling client sockets */
    // TODO needs to be final
    private ConnectionManager connectionManager;

    private final Thread heartbeat = new Thread(() -> {
        Thread.currentThread().setName("Heartbeat");
        while (true) {
            LOG.info(String.format("connections=%s activeThreads=%s",
                    this.connectionManager.getConnectionCount(),
                    Thread.activeCount()));
            this.connectionManager.harvestDeadSockets();
            try {
                Thread.sleep(serverConfiguration.HEARTBEAT_INTERVAL_MS);
            } catch (InterruptedException e) {
                LOG.info("Heartbeat interrupted, stopping thread");
                break;
            }
        }
    });


    public ClownfishServer(CfConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
        this.connectionManager = new ConnectionManager(this.serverConfiguration);
        this.addShutdownHooks();
        try {
            this.socket = new ServerSocket(this.serverConfiguration.PORT);
            LOG.info(String.format("Started clownfish server [%s] to port={%s}",
                    this.serverConfiguration.CLOWNFISH_VERSION,
                    this.serverConfiguration.PORT));
        } catch (IOException exception) {
            throw new UnrecoverableCFException("Can't start clownfish", exception);
        }
        this.startHeartbeat();
    }

    private void startHeartbeat() {
        if(serverConfiguration.HEARTBEAT_ENABLED) {
            LOG.info(String.format("Starting heartbeat with interval=%s", serverConfiguration.HEARTBEAT_INTERVAL_MS));
            this.heartbeat.start();
        } else {
            LOG.info("Heartbeat not enabled");
        }
    }

    private void addShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Server shutdown initiated");
            this.connectionManager.shutdown();
            this.heartbeat.interrupt();
        }));
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket client = this.socket.accept();
                this.connectionManager.connect(client);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
