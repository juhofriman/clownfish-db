package fi.cdfdb;

import fi.cdfdb.configuration.CfConfiguration;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ConnectionManager {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private final LinkedList<Socket> clientSockets = new LinkedList<>();

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    private final CfConfiguration serverConfiguration;

    public ConnectionManager(CfConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

    public synchronized void connect(Socket socket) {

        LOG.info(String.format("Accepted connection from peer ip=%s", socket.getInetAddress()));
        this.clientSockets.add(socket);

        try {
            executor.submit(new ServerSideClientWorker(
                    this.serverConfiguration,
                    () -> {
                        try {
                            socket.close();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    },
                    new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream())));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void harvestDeadSockets() {
        synchronized (this) {
            for (Socket clientSocket : this.clientSockets) {
                if (clientSocket.isClosed()) {
                    LOG.info(String.format("Harvesting dead client socket ip=%s", clientSocket.getInetAddress()));
                    this.clientSockets.remove(clientSocket);
                }
            }
        }
    }

    public void shutdown() {
        for (Socket clientSocket : clientSockets) {
            try {
                if (clientSocket.isClosed()) {
                    LOG.warning("Client closed already");
                } else {
                    LOG.info("Sending shutdown message to client and closing socket");
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Server shutdown initiated, kicking you out");
                    clientSocket.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public Integer getConnectionCount() {
        return this.clientSockets.size();
    }
}