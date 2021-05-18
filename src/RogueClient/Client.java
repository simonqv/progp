package RogueClient;// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.net.*;
import java.io.*;



public class Client {
    private OutputStream output;
    private InputStream input;
    private int id;
    private BoardGUI boardGUI = null;


    public void runClient() {
            String hostname = "localhost";
            int port = 9999;

            try (Socket socket = new Socket(hostname, port)) {

                // Output - send to server
                output = socket.getOutputStream();

                // Input - received from server
                input = socket.getInputStream();

                // Connect
                output.write(new byte[]{0, Byte.parseByte("0"), Byte.parseByte("0")});
                int width = 0;
                int height = 0;
                int connectCode = input.read();
                if (connectCode == 0) {
                    id = input.read();
                    width = input.read();
                    height = input.read();
                } else {
                    System.out.println("Not connected.");
                    System.exit(-2);
                }

                int code = input.read();
                if (code == 1) {
                    boardGUI = new BoardGUI(input, width, height, id);
                }

                /*
                Ska ta emot spelplanen och skriva ut, även när den andra spelaren gjort något, ska ej fastna på att vänta
                på input från denna spelare...
                ska skicka actions till servern.
                 */
                do {
                    boardGUI.gameWindow(width, height, this);
                    // Read first sign from input. If 1, update board...
                    code = input.read();
                    if (code == 1) {
                        boardGUI.updateMap(input, width, height);
                    }
                    if (code == 2) {
                        boardGUI.updateInventory(input);
                    }

                } while (true);

            } catch (UnknownHostException ex) {
                System.out.println("Server.Server not found: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println("I/O error: " + ex.getMessage());
            }
        }

        public void send(int action) throws IOException {
            output.write(new byte[]{1, (byte) id, (byte) action});
        }

}
