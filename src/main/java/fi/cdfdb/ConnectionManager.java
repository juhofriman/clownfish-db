package fi.cdfdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class ConnectionManager {

    private final LinkedList<Socket> clientSockets = new LinkedList<>();

    public void connect(Socket socket) {
        this.clientSockets.add(socket);
        new Thread(() -> {
            System.out.println("Starting thread");
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String input = in.readLine();
                    if(input == null) {
                        System.out.println("[" + Thread.currentThread().getName() + "] Input null");
                        socket.close();
                        break;
                    } else {
                        System.out.println("[" + Thread.currentThread().getName() + "] " +  input);
                        out.println("Thanks for calling! You said: " + input);
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            for (Socket clientSocket : this.clientSockets) {
                if(clientSocket.isClosed()) {
                    System.out.println("Harvesting dead client socket");
                    this.clientSockets.remove(clientSocket);
                }
            }

        }).start();
    }

    public void shutdown() {
        for (Socket clientSocket : clientSockets) {
            try {
                if (clientSocket.isClosed()) {
                    System.out.println("Closed client");
                } else {
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println("Server shutdown...");
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