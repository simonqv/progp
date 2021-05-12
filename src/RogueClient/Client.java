package RogueClient;// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.net.*;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class Client {

    /**
     * This program demonstrates a simple TCP/IP socket client that reads input
     * from the user and prints echoed message from the server.
     *
     * @author www.codejava.net
     */

        public void runClient() {

            String hostname = "localhost";
            int port = 9999;

            try (Socket socket = new Socket(hostname, port)) {

                // Output - send to server
                OutputStream output = socket.getOutputStream();

                // Input - received from server
                InputStream input = socket.getInputStream();

                // Connect
                output.write(new byte[]{0, Byte.parseByte("0"), Byte.parseByte("0")});
                int width = 0;
                int height = 0;
                int connectCode = input.read();

                if (connectCode == 0) {
                    int id = input.read();
                    width = input.read();
                    height = input.read();
                } else {
                    System.out.println("Not connected.");
                    System.exit(-2);
                }

                do {
                    // Read first sign from input. If 1, update board...
                    int code = input.read();
                    if (code == 1) {
                        BoardParser bp = new BoardParser(input, width, height);
                        bp.printer(width, height);
                    }


                } while (true);

            } catch (UnknownHostException ex) {
                System.out.println("Server.Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
            }
        }
}
