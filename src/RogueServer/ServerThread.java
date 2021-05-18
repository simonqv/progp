package RogueServer;

import RogueCommon.CommandConstants;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Handles actions etc.
 */
public class ServerThread extends Thread implements GameBoardListener {
    private final Socket socket;
    private final int id;
    private final GameBoard myGame;
    private Player player;

    private OutputStream output;

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
            output = socket.getOutputStream();

            // Connection
            int requestCommand = input.read();
            if (requestCommand == CommandConstants.CONNECT) {
                input.readNBytes(2);
                // Send code 0, player/client ID, and size of map to Client.
                output.write(new byte[]{
                        CommandConstants.CONNECT,
                        (byte) id,
                        (byte) myGame.getWidth(),
                        (byte) myGame.getHeight()});
            }

            player = new Player(id, myGame);
            myGame.placePlayer(player);


                /*
                Behöver fixa så att först spelplanen skickas till båda clienterna kontinuerligt.
                Behöver även lyssna på om den får någon action input från clienten. Får den från ena måste bådas
                spelplaner uppdateras
                 */
            do {
                sendGameBoard(output);
                sendInventory(output);

                // code from client for action
                int code = input.read();
                if (code == CommandConstants.ACTION) {
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

    private void sendInventory(OutputStream output) throws IOException {
        // Convert inventory to byteArray.
        if (player.inventory != null) {
            /*
             * The string representation of the inventory to send to client.
             * Format: comma-seperated list of key-value pairs. Like: #.item,#.item,#.item
             */
            byte[] invMap = player.inventory
                    .getItems()
                    .stream()
                    .map(Item::toString)
                    .collect(Collectors.joining(","))
                    .getBytes(StandardCharsets.US_ASCII);

            // Send the byteArray to the Client.
            output.write(CommandConstants.INVENTORY);
            output.write(invMap);
            output.write(0);
        }
    }

    private void sendGameBoard(OutputStream output) throws IOException {
        // Convert game map to byteArray.
        byte[] byteMap = myGame.toByte();
        // Send the byteArray to the Client.
        output.write(CommandConstants.BOARD);
        output.write(byteMap);
    }

    @Override
    public void boardChanged() {
        try {
            sendGameBoard(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


