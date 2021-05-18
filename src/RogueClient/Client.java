package RogueClient;// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.net.*;
import java.io.*;



public class Client {

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
                System.out.println(connectCode);
                if (connectCode == 0) {
                    int id = input.read();
                    width = input.read();
                    height = input.read();
                } else {
                    System.out.println("Not connected.");
                    System.exit(-2);
                }

                /*
                Ska ta emot spelplanen och skriva ut, även när den andra spelaren gjort något, ska ej fastna på att vänta
                på input från denna spelare...
                ska skicka actions till servern.
                 */
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
