package RogueServer;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Handles actions etc.
 */
public class ServerThread extends Thread {
        private Socket socket;
        private int id;
        private GameBoard myGame;
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

                // fixa placePlayer, för olika pos för olika spelare
                player = new Player(id, myGame);
                myGame.placePlayer(player);

                do {
                    int code = input.read();

                    if (code == 1) {
                        // act is ID and type of action.
                        byte[] act = input.readNBytes(2);
                        player.action(act, myGame);
                    }
                    byte[] act = null;

                    // Konvertera spelbrädet till byte array
                    byte[] byteMap = myGame.toByte();
                    // Skicka spelbrädet till clienten!
                    TimeUnit.SECONDS.sleep(10);
                    output.write(byteMap);



                } while (true);
                /*
                 text = reader.readLine();
                    String reverseText = new StringBuilder(text).reverse().toString();
                    writer.println("Server.Server: " + reverseText);
                 */

//                socket.close();
            } catch (IOException | InterruptedException ex) {
                System.out.println("Server.Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


