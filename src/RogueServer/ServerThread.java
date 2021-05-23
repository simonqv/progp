package RogueServer;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import RogueCommon.CommandConstants;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * Handles the separate clients
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

    /**
     * Runs the thread
     */
    public void run() {
        try {
            // Input from Client.
            InputStream input = socket.getInputStream();

            // Output to Client.
            output = socket.getOutputStream();

            // Connection request.
            int requestCommand = input.read();
            if (requestCommand == CommandConstants.CONNECT) {
                input.readNBytes(2);
                // Send code CONNECT (0), player/client ID, and size of map to Client.
                output.write(new byte[]{
                        CommandConstants.CONNECT,
                        (byte) id,
                        (byte) myGame.getWidth(),
                        (byte) myGame.getHeight()});
            }

            player = new Player(id, myGame);
            myGame.placePlayer(player);

            do {
                try {
                    sendGameBoard(output);
                    sendInventory(output);

                    // code from client for action
                    int code = input.read();
                    if (code == CommandConstants.ACTION) {
                        // act is ID and type of action.
                        byte[] act = input.readNBytes(2);
                        player.action(act, myGame);
                    }

                } catch (SocketException se) {
                    System.out.println("Client " + id + " disconnected!");
                    myGame.removeClient(this);
                    break;
                }
            } while (true);

        } catch (IOException ex) {
            System.out.println("Server connection exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Send the inventory as null terminated byte array with INVENTORY (11) code first
     * @param output the output connection to client. (What to send)
     */
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

    /**
     * Sends fixed size byte array of game board to client.
     * @param output the output connection to client.
     */
    private void sendGameBoard(OutputStream output) throws IOException {
        // Convert game map to byteArray.
        byte[] byteMap = myGame.toByte();
        // Send the byteArray to the Client.
        output.write(CommandConstants.BOARD);
        output.write(byteMap);
    }

    /**
     * Send the nul-terminated byte array of winning message to client.
     * @param output the output connection to client.
     */
    private void sendWinner(OutputStream output) throws IOException {
        // Send message that game is done.
        String m = "WIN!! THE GAME IS OVER! :^)" ;
        byte[] byteString = m.getBytes();
        // Send the byteArray to the Client.
        output.write(CommandConstants.MESSAGE);
        output.write(byteString);
        output.write(0);
    }

    /**
     * Update game board.
     */
    @Override
    public void boardChanged() {
        try {
            sendGameBoard(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Game win.
     */
    @Override
    public void winner() {
        try {
            sendWinner(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }
}


