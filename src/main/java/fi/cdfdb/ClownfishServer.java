package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;
import fi.cdfdb.exception.UnrecoverableCFException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClownfishServer implements Runnable {

    /** Runtime configuration of the clownfish server */
    private CfConfiguration serverConfiguration;

    /** Server socket instance of Clownfish */
    private final ServerSocket socket;

    /** Connection manager for handling client sockets */
    private final ConnectionManager connectionManager = new ConnectionManager();

    private final Thread heartbeat = new Thread(() -> {
        Thread.currentThread().setName("Heartbeat");
        while (true) {
            System.out.println("[" + Thread.currentThread().getName() + "] connections=" +
                    this.connectionManager.getConnectionCount() + " activeThreads=" + Thread.activeCount());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("HEARTBEAT INTERRUPTED");
                break;
            }
        }
    });

    public ClownfishServer(CfConfiguration serverConfiguration) {
        this.addShutdownHooks();
        this.startHeartbeat();
        this.serverConfiguration = serverConfiguration;
        try {
            this.socket = new ServerSocket(this.serverConfiguration.PORT);
            System.out.println(String.format("Started clownfish server to port={%s}", this.serverConfiguration.PORT));
        } catch (IOException exception) {
            throw new UnrecoverableCFException("Can't start clownfish", exception);
        }
    }

    private void startHeartbeat() {
        this.heartbeat.start();
    }

    private void addShutdownHooks() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Server shutdown");
            this.connectionManager.shutdown();
            this.heartbeat.interrupt();
        }));
    }

    @Override
    public void run() {

        while(true) {
            try {
                System.out.println("Waiting for conn...");
                Socket client = this.socket.accept();

                this.connectionManager.connect(client);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
