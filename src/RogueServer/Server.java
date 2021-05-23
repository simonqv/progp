package RogueServer;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Server class. Starts the server and server threads for each new client.
 */
public class Server {

    private final List<GameBoardListener> clients = new ArrayList<>();

    public void runServer() throws IOException {
        int port = 9999;
        int id = 0;
        ServerSocket serverSocket = new ServerSocket(port);

        // Initialize board.
        GameBoard myGame = new GameBoard(clients);
        myGame.buildGameMap();
        myGame.populateMap();

        System.out.println("Server.Server is listening on port " + port);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                id++;
                ServerThread thread = new ServerThread(socket, id, myGame);
                thread.start();
                // Adds to listeners
                clients.add(thread);
            } catch (SocketException se) {
                System.out.println("Connection reset");
            }
        }

    }

    /**
     * Runner.
     */
    public static void main(String[] args) throws IOException {
        new Server().runServer();
    }
}
