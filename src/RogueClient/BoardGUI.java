package RogueClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BoardGUI {
    private byte[] mapArray;
    JFrame frame;

    public BoardGUI(InputStream inputStream, int w, int h, int id) throws IOException {
        this.mapArray = inputStream.readNBytes(h * w);
        this.frame = new JFrame("Game Board " + id);
        frame.setSize(2000, 1500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateMap(InputStream inputStream, int w, int h) throws IOException {
        mapArray = inputStream.readNBytes(h * w);
    }

    public void gameWindow(int w, int h, Client client) {
        JTextArea board = new JTextArea(); 
        board.setFont(new Font("monospaced", Font.PLAIN, 12));
        board.setText(boardToString(w, h));
        board.addKeyListener(new CustomKeyListener(client));
        frame.add(board);
        frame.setVisible(true);
        frame.pack();
    }

    private String boardToString(int w, int h) {
        StringBuilder sb = new StringBuilder();
        int k = 0;
        String s = new String(mapArray, StandardCharsets.US_ASCII);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                sb.append(s.charAt(k));
                sb.append(" ");
                k++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void updateInventory(InputStream input) throws IOException {
        JTextArea inventory = new JTextArea();
        inventory.setText(invToString(input));
        frame.add(inventory, BorderLayout.PAGE_END);
    }

    private String invToString(InputStream input) throws IOException {
        StringBuilder sb = new StringBuilder();

        while (true) {
            int b = input.read();

            if (b == 0) break;
            if ((char) b == ',' || (char) b == '_') {
                b = ' ';
            } else if ((char) b == '.') {
                b = ':';
            }
            sb.append((char) b);
        }

        return sb.toString();
    }

    public void displayMessage(InputStream input) throws IOException {
        frame.getContentPane().removeAll();
        JTextArea messageScreen = new JTextArea();
        StringBuilder sb = new StringBuilder();

        while (true) {
            int b = input.read();
            if (b == 0) break;
            sb.append((char) b);
        }
        String message = sb.toString();
        messageScreen.setText(message);
        frame.add(messageScreen, BorderLayout.CENTER);
        frame.validate();
    }
}
