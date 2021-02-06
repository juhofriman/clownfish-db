package fi.cdfdb.cli;

import fi.cdfdb.protocol.CfClientHandshake;
import fi.cdfdb.protocol.CfQuery;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ClownfishCli {

    private final Socket clientSocket;
    private final DataOutputStream out;
    private final DataInputStream in;

    public ClownfishCli() throws IOException {
        clientSocket = new Socket("localhost", 1234);
        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());
    }

    private void run() {

        try (Scanner scanner = new Scanner(System.in)) {

            Supplier<String> input = () -> {
                System.out.print("> ");
                return scanner.nextLine();
            };

            String quit = ":quit";
            String manualHandshake = ":handshake";
            Function<String, String> expressionHandler = expression -> {

                if (quit.equals(expression)) {
                    return quit;
                }

                try {
                    if(manualHandshake.equals(expression)) {
                        out.write(new CfClientHandshake().serialize());
                    } else {
                        out.write(new CfQuery(expression).serialize());
                    }
                    byte type = in.readByte();
                    short length = in.readShort();
                    System.out.println(String.format(" -- Message of type=%s length=%s", type, length));
                    byte[] payload = new byte[length];
                    in.readFully(payload);
                    System.out.println(String.format(" -- Received message: %s", new String(payload, StandardCharsets.UTF_8)));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                return "OK";

            };

            Predicate<String> quitCommand = (command) -> quit.equalsIgnoreCase(command.trim());

            Stream.generate(input).map(expressionHandler).noneMatch(quitCommand);
        }
    }

    public static void main(String[] args) {
        try {
            new ClownfishCli().run();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
