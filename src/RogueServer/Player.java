package RogueServer;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    int name;
    int wPos;
    int hPos;
    Inventory inventory;

    public Player(int name, GameBoard game) {
        this.name = name;
        this.hPos = 1;
        this.wPos = getStartCoordinates(game, 19);

    }

    public String getNameString() {
        return String.valueOf(name);
    }

    public int getStartCoordinates(GameBoard game, int wGuess) {
        int w = wGuess;
        while (true) {
            if (game.gameMap[hPos][w].equals(" ")) {
                return w;
            } else {
                w++;
            }
        }
    }

    /**
     * move codes: 1 = Left, 2 = Up, 3 = Right, 4 = Down.
     * @param action
     */
    public void move(int action, GameBoard game) {
        int hOld = hPos;
        int wOld = wPos;
        if (action == 1) {
            wPos--;
        } else if (action == 2) {
            hPos--;
        } else if (action == 3) {
            wPos++;
        } else if (action == 4) {
            hPos++;
        }
        game.movePlayer(this, hOld, wOld);
    }

    /**
     * Move or attack etc.
     * @param act what type of action to perform
     */
    public void action(byte[] act, GameBoard game) {
        if (act.length > 2) {
            //fixa throw
            return;
        }
        int id = act[0];
        int action = act[1];
        if (action == 1 || action == 2 || action == 3 || action == 4) {
            move(action, game);
        } else if (action == 5) {
            attack(action, game);
        }

        // 12
        // 2 ropa på move
        // Kolla vilken kod.
    }

    private void attack(int action, GameBoard game) {
    }

}
