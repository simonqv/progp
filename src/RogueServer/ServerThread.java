package RogueServer;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Handles actions etc.
 */
public class ServerThread extends Thread {
        private final Socket socket;
        private final int id;
        private final GameBoard myGame;
        private Player player;

        public ServerThread(Socket socket, int id, GameBoard myGame) {
            this.socket = socket;
            this.id = id;
            this.myGame = myGame;
        }

        public void run() {
            try {
                // Input from Client.
                InputStream input = socket.getInputStream();

                // Output to Client.
                OutputStream output = socket.getOutputStream();

                // Connection
                int reqCode = input.read();
                if (reqCode == 0) {
                    input.readNBytes(2);
                    // Send code 0, player/client ID, and size of map to Client.
                    output.write(new byte[]{0, (byte) id, (byte) myGame.getWidth(), (byte) myGame.getHeight()});

                }

                player = new Player(id, myGame);
                myGame.placePlayer(player);


                /*
                Behöver fixa så att först spelplanen skickas till båda clienterna kontinuerligt.
                Behöver även lyssna på om den får någon action input från clienten. Får den från ena måste bådas
                spelplaner uppdateras
                 */
                do {
                    // Convert game map to byteArray.
                    byte[] byteMap = myGame.toByte();
                    // Send the byteArray to the Client.
                    output.write(byteMap);

                    // Convert inventory to byteArray.
                    if (player.inventory != null) {
                        byte[] invMap = player.inventory.byteInventory();
                        // Send the byteArray to the Client.
                        output.write(invMap);
                    }

                    // code from client for action
                    int code = input.read();
                    if (code == 1) {
                        // act is ID and type of action.
                        byte[] act = input.readNBytes(2);
                        player.action(act, myGame);
                    }



                } while (true);

            } catch (IOException ex) {
                System.out.println("Server.Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


