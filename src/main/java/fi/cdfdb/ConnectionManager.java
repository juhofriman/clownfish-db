package fi.cdfdb;

import fi.cdfdb.protocol.CfHandshake;
import fi.cdfdb.protocol.CfMessage;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream());

                while (true) {
                    byte type = in.readByte();
                    short length = in.readShort();
                    LOG.info(String.format("Message of type=%s length=%s", type, length));
                    byte[] payload = new byte[length];
                    in.readFully(payload);
                    LOG.info(String.format("Received message: %s", new String(payload, StandardCharsets.UTF_8)));
                    out.write(new CfHandshake().serialize());
                }
            } catch (IOException exception) {
                if(exception instanceof  EOFException) {
                    LOG.info("Client hung up");
                } else {
                    exception.printStackTrace();
                }
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