package RogueServer;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

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
        inventory.addItem(new Item(Item.ItemType.COINS, 0));
    }

    public char getNameChar() {
        return String.valueOf(name).charAt(0);
    }

    /**
     * Find were to spawn player.
     * @param game the game
     * @param wGuess first guess on x-axis position
     * @return the actual x-axis position (w)
     */
    public int getStartCoordinates(GameBoard game, int wGuess) {
        int w = wGuess;
        while (true) {
            if (game.gameMap[hPos][w] == ' ') {
                return w;
            } else {
                w++;
            }
        }
    }

    /**
     * Change player coordinates.
     * move codes: 1 = Left, 2 = Up, 3 = Right, 4 = Down.
     * @param direction to move.
     * @param game the current game played.
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

        char newPos = game.gameMap[hPos][wPos];
        // If matching something we are not allowed to stand on, don't change position.
        if (String.valueOf(newPos).matches("[#+\\d]")) {
            hPos = hOld;
            wPos = wOld;
        // If new position contains COIN or KEY pick it up.
        } else if (newPos == GameBoard.COIN) {
            for (Item item : inventory.getItems()) {
                if (item.getItem() == Item.ItemType.COINS) {
                    item.increaseQuantity();
                }
            }
        } else if (newPos == GameBoard.FIRSTKEY) {
            inventory.addItem(new Item(Item.ItemType.FIRST_KEY, 1));
        } else if (newPos == GameBoard.SECONDKEY) {
            inventory.addItem(new Item(Item.ItemType.SECOND_KEY, 1));
        }
        game.movePlayer(this, hOld, wOld);
    }

    /**
     * Choose correct action (made so that it is possible and easy to implement more actions, such as attack etc.)
     * @param act what type of action to perform
     */
    public void action(byte[] act, GameBoard game) {
        if (act.length > 2) {
            return;
        }
        int id = act[0];
        int action = act[1];
        if (action == 1 || action == 2 || action == 3 || action == 4) {
            move(action, game);
        }

    }
}
