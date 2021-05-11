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
                    output.write(new byte[]{0, (byte) id, (byte) myGame.getWidth(), (byte) myGame.getHeight()});
                }

                /*
                 text = reader.readLine();
                    String reverseText = new StringBuilder(text).reverse().toString();
                    writer.println("Server.Server: " + reverseText);
                 */
                do {
                    //myGame.moveCharacter(byte[] action);

                    // Konvertera spelbrädet till byte array
                    byte[] byteMap = myGame.toByte();
                    // Skicka spelbrädet till clienten!
                    TimeUnit.SECONDS.sleep(1);
                    output.write(byteMap);

                } while (true);

//                socket.close();
            } catch (IOException | InterruptedException ex) {
                System.out.println("Server.Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


