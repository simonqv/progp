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
        inventory.addItem(new Item(Item.ItemType.COINS, 30));
    }

    public char getNameString() {
        return String.valueOf(name).charAt(0);
    }

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

        char newPos = game.gameMap[hPos][wPos];
        if (String.valueOf(newPos).matches("[#+\\d]")) {
            hPos = hOld;
            wPos = wOld;
        } else if (newPos == GameBoard.COIN) {
            inventory.addItem(new Item(Item.ItemType.COINS, 1));
        } else if (newPos == GameBoard.FIRST_KEY) {
            inventory.addItem(new Item(Item.ItemType.FIRST_KEY, 1));
        } else if (newPos == GameBoard.SECOND_KEY) {
            inventory.addItem(new Item(Item.ItemType.SECOND_KEY, 1));
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
     *
     * @param item to check if in inventory.
     * @return true if inventory contains specified item.
     */
    public boolean invContains(Item item) {
        return inventory.getItems().contains(item);
    }

    private void attack(int action, GameBoard game) {
    }

}
