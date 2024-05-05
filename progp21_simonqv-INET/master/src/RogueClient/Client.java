package RogueClient;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import RogueCommon.CommandConstants;

import java.net.*;
import java.io.*;


public class Client {
    private OutputStream output;
    private InputStream input;
    private int id;
    private BoardGUI boardGUI = null;

    /**
     * Run the client and connect to server.
     */
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
            if (connectCode == CommandConstants.CONNECT) {
                id = input.read();
                width = input.read();
                height = input.read();
            } else {
                System.out.println("Not connected.");
                System.exit(-2);
            }

            // Create game board after connection.
            int code = input.read();
            if (code == CommandConstants.BOARD) {
                boardGUI = new BoardGUI(input, width, height, id);
            }

            do {
                // Display the game.
                boardGUI.gameWindow(width, height, this);

                // Read first sign from input. If 10, update board...
                code = input.read();

                switch (code) {
                    case CommandConstants.BOARD -> boardGUI.updateMap(input, width, height);
                    case CommandConstants.INVENTORY -> boardGUI.updateInventory(input);
                    case CommandConstants.MESSAGE -> boardGUI.displayMessage(input);
                }
            } while (code != CommandConstants.MESSAGE);

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    // In case of KEY EVENT, send action to server.
    public void send(int action) throws IOException {
        output.write(new byte[]{
                CommandConstants.ACTION,
                (byte) id,
                (byte) action});
    }

    /**
     * To run the client.
     */
    public static void main(String[] args) {
        new Client().runClient();
    }
}
