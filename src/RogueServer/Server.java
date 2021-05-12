package RogueServer;// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void runServer() throws IOException {
    //public static void main(String[] args) throws IOException {
        int port = 9999;
        int id = 0;
        ServerSocket serverSocket = new ServerSocket(port);

        // Initialize board.
        GameBoard myGame = new GameBoard();
        myGame.buildGameMap();

        System.out.println("Server.Server is listening on port " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");
            id ++;
            new ServerThread(socket, id, myGame).start();
        }

    }

}
