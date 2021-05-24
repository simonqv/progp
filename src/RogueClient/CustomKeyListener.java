package RogueClient;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class CustomKeyListener implements KeyListener {
    private final Client client;

    public CustomKeyListener(Client client) {
        this.client = client;
    }

    /**
     * Detects button press on keyboard.
     * @param e the key event, keyboard press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        try {
            // 1 = LEFT action, 2 = UP action, 3 = RIGHT action, 4 = DOWN action.
            switch (keyCode) {
                case KeyEvent.VK_UP -> client.send(2);
                case KeyEvent.VK_DOWN -> client.send(4);
                case KeyEvent.VK_LEFT -> client.send(1);
                case KeyEvent.VK_RIGHT -> client.send(3);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
