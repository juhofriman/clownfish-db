package fi.cdfdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class ConnectionManager {

    private final Logger LOG = Logger.getLogger(getClass().getName());

    private final LinkedList<Socket> clientSockets = new LinkedList<>();

    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public void connect(Socket socket) {
        LOG.info(String.format("Accepted connection from peer ip=%s", socket.getInetAddress()));
        this.clientSockets.add(socket);

        executor.submit(new Thread(() -> {
            LOG.info("Starting new thread");
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String input = in.readLine();
                    if(input == null) {
                        LOG.info("InputStream closed");
                        socket.close();
                        break;
                    } else {
                        LOG.info(String.format("Received message: %s", input));
                        out.println("Thanks for calling! You said: " + input);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            for (Socket clientSocket : this.clientSockets) {
                if(clientSocket.isClosed()) {
                    LOG.info(String.format("Harvesting dead client socket ip=%s", socket.getInetAddress()));
                    this.clientSockets.remove(clientSocket);
                }
            }

        }));
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