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
        inventory = new Inventory();
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
     * @param direction to move.
     */
    public void move(int direction, GameBoard game) {
        int hOld = hPos;
        int wOld = wPos;
        if (direction == 1) {
            wPos--;
        } else if (direction == 2) {
            hPos--;
        } else if (direction == 3) {
            wPos++;
        } else if (direction == 4) {
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

    }

    /**
     * Calls addItem from Inventory.java to update the players inventory.
     * @param item to add to the inventory.
     */
    public void addToInv(Item item) {
        inventory.addItem(item);
    }

    /**
     *
     * @param item to check if in inventory.
     * @return true if inventory contains specified item.
     */
    public boolean invContains(Item item) {
        return inventory.inventory.contains(item);
    }

    private void attack(int action, GameBoard game) {
    }

}
