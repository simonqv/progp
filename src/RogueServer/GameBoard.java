package RogueServer;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameBoard {
    // Size and gameBoard
    public int width = 50;
    public int height = 40;
    public char[][] gameMap;

    // Constants
    public static final char DIRT = '#';
    public static final char CAVE = ' ';
    public static final char DOOR = '+';
    public static final char COIN = '*';
    public static final char BUTTON = 'B';
    public static final char FIRSTKEY = '?';
    public static final char SECONDKEY = '!';
    public final int numberOfCoins = 50;

    // Coordinates for keys
    int[] firstKeyPosition = new int[]{26, 22};
    int[] secondKeyPosition = new int[]{22, 46};

    // Coordinates for doors
    int[] buttonDoor = new int[]{11, 40};
    int[] sndButtonDoor = new int[]{31, 12};
    int[] firstKeyDoor = new int[]{5, 18};
    int[] secondKeyDoor = new int[]{38, 24};

    // Coordinates for buttons
    // Button one: x,y, Button two: x,y.
    int[] fstDoorButtons = new int[]{10, 39, 10, 41};
    int[] sndDoorButtons = new int[]{26, 9, 34, 5};

    // array of door positions
    int[][] doorPositions = new int[][]{buttonDoor, sndButtonDoor, firstKeyDoor, secondKeyDoor}; // 3 doors...

    private final List<GameBoardListener> listeners;

    /**
     * Create a new game board.
     *
     * @param listeners
     */
    public GameBoard(List<GameBoardListener> listeners) {
        this.listeners = listeners;
        this.gameMap = new char[height][width];
        for (char[] row : gameMap) {
            Arrays.fill(row, DIRT);
        }
    }

    /**
     * Fill the game map with caves, tunnels and walls.
     */
    public void buildGameMap() {
        // Build all caves.
        addCave(3, 3, 7, 9, CAVE);    // 7x9
        addCave(1, 19, 6, 12, CAVE);  // Start cave 6x12
        addCave(3, 35, 8, 10, CAVE);  // 8x10

        addCave(13, 10, 6, 19, CAVE); // 6x19
        addCave(16, 36, 8, 12, CAVE); // 8x12
        addCave(22, 20, 6, 5, CAVE);  // Small cave 6x5

        addCave(25, 3, 11, 9, CAVE);  // 11x9
        addCave(30, 20, 8, 10, CAVE); // 8x10
        addCave(30, 36, 8, 12, CAVE); // 8x12

        // Build all horizontal tunnels.
        addHorizontalTunnel(5, 13, 6);
        addHorizontalTunnel(5, 32, 3);
        addHorizontalTunnel(17, 30, 6);
        addHorizontalTunnel(31, 13, 7);
        addHorizontalTunnel(33, 31, 5);

        // Build all vertical tunnels.
        addVerticalTunnel(12, 40, 4);
        addVerticalTunnel(25, 40, 5);
        addVerticalTunnel(20, 22, 2);
        addVerticalTunnel(11, 5, 14);
        addVerticalTunnel(38, 24, 2);

        // Draw a wall in the cave to the right.
        addCave(19, 36, 1, 12, DIRT);

        addDoors();
    }

    /**
     * Places the doors on the map.
     */
    private void addDoors() {
        for (int[] doorPosition : doorPositions) {
            gameMap[doorPosition[0]][doorPosition[1]] = DOOR;

            // If door has corresponding buttons, place them as well.
            if (doorPosition[0] == buttonDoor[0] && doorPosition[1] == buttonDoor[1]) {
                gameMap[fstDoorButtons[0]][fstDoorButtons[1]] = BUTTON;
                gameMap[fstDoorButtons[2]][fstDoorButtons[3]] = BUTTON;
            }
            if (doorPosition[0] == sndButtonDoor[0] && doorPosition[1] == sndButtonDoor[1]) {
                gameMap[sndDoorButtons[0]][sndDoorButtons[1]] = BUTTON;
                gameMap[sndDoorButtons[2]][sndDoorButtons[3]] = BUTTON;
            }

        }
    }

    /**
     * Place all items on the game board.
     */
    public void populateMap() {
        placeKey(FIRSTKEY, firstKeyPosition[0], firstKeyPosition[1]);
        placeKey(SECONDKEY, secondKeyPosition[0], secondKeyPosition[1]);
        placeCoins(numberOfCoins);
    }

    /**
     * Place character on the map.
     *
     * @param player the player that's going to move.
     */
    public void placePlayer(Player player) {
        gameMap[player.hPos][player.wPos] = player.getNameChar();

        // Unlock button-door if buttons are pressed.
        if (buttonsArePressed(fstDoorButtons)) {
            unlockDoor(buttonDoor);
        } else if (buttonsArePressed(sndDoorButtons)) {
            unlockDoor(sndButtonDoor);

        // Unlocks doors with keys
        } else if (player.inventory.exists(new Item(Item.ItemType.FIRST_KEY, 0)) && player.hPos == firstKeyDoor[0] && player.wPos == firstKeyDoor[1] + 1) {
            unlockDoor(firstKeyDoor);
        } else if (player.inventory.exists(new Item(Item.ItemType.SECOND_KEY, 0)) && player.hPos == secondKeyDoor[0] - 1 && player.wPos == secondKeyDoor[1]) {
            unlockDoor(secondKeyDoor);

        // If one player located on exit coordinates (win)
        } else if (player.hPos == secondKeyDoor[0] + 1 && player.wPos == secondKeyDoor[1]) {
            listeners.forEach(GameBoardListener::boardChanged);
            // It is a win!
            listeners.forEach(GameBoardListener::winner);
        }

        // Notify all listeners of the changed game board
        listeners.forEach(GameBoardListener::boardChanged);
    }

    /**
     * Change the players position on board.
     */
    public void movePlayer(Player player, int hOld, int wOld) {
        // If position was button, keep button, else make empty
        if (hOld == fstDoorButtons[0] && wOld == fstDoorButtons[1] || hOld == fstDoorButtons[2] && wOld == fstDoorButtons[3] || hOld == sndDoorButtons[0] && wOld == sndDoorButtons[1] || hOld == sndDoorButtons[2] && wOld == sndDoorButtons[3]) {
            gameMap[hOld][wOld] = BUTTON;
        } else {
            gameMap[hOld][wOld] = CAVE;
        }

        // Place the player on the next position.
        placePlayer(player);
    }

    /**
     * Check if both door buttons are pressed.
     * @param buttons The buttons to check.
     * @return true if pressed.
     */
    public boolean buttonsArePressed(int[] buttons) {
        return gameMap[buttons[0]][buttons[1]] != BUTTON && gameMap[buttons[2]][buttons[3]] != BUTTON;
    }

    /**
     * Replace DOOR symbol with empty when opening them.
     * @param door The door to open.
     */
    public void unlockDoor(int[] door) {
        gameMap[door[0]][door[1]] = CAVE;
    }

    /**
     * Converts the game board to a byte array.
     * @return the byte array.
     */
    public byte[] toByte() {
        // convert to byte!
        int k = 0;
        byte[] byteBoard = new byte[height * width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                byteBoard[k] = (byte) gameMap[i][j];
                k++;
            }
        }
        return byteBoard;
    }

    /**
     * Draw cave on game board based on specified coordinates and cave size.
     *
     * @param rowPosition Upper left corner row
     * @param colPosition Upper left corner column
     * @param rowSize How many rows
     * @param colSize How many columns
     */
    public void addCave(int rowPosition, int colPosition, int rowSize, int colSize, char caveSymbol) {
        for (int row = rowPosition; row < rowPosition + rowSize; row++) {
            for (int col = colPosition; col < colPosition + colSize; col++) {
                gameMap[row][col] = caveSymbol;
            }
        }
    }

    /**
     * Draw a horizontal tunnel on game board.
     *
     * @param rowPosition Start row.
     * @param colPosition Start column.
     * @param length How long.
     */
    public void addHorizontalTunnel(int rowPosition, int colPosition, int length) {
        for (int col = colPosition - 1; col < colPosition + length; col++) {
            gameMap[rowPosition][col] = CAVE;
        }
    }

    /**
     * Draw a vertical tunnel on game board.
     *
     * @param rowPosition Start row.
     * @param colPosition Start column.
     * @param length How long.
     */
    public void addVerticalTunnel(int rowPosition, int colPosition, int length) {
        for (int row = rowPosition - 1; row < rowPosition + length; row++) {
            gameMap[row][colPosition] = CAVE;
        }
    }

    /**
     * Get the width of board.
     *
     * @return width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of board.
     *
     * @return height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Place all coins on game board.
     *
     * @param numberOfCoins How many coins to place.
     */
    public void placeCoins(int numberOfCoins) {
        int num = numberOfCoins;
        int row;
        int col;
        while (num != 0) {
            row = generateRandomIndex(getHeight());
            col = generateRandomIndex(getWidth());
            if (gameMap[row][col] == CAVE) {
                gameMap[row][col] = COIN;
                num--;
            }
        }
    }

    /**
     * Add a key to game board.
     *
     * @param key Char representing the key.
     * @param row row coordinate.
     * @param col column coordinate.
     */
    public void placeKey(char key, int row, int col) {
        gameMap[row][col] = key;
    }

    /**
     * Generates a random index between lower bound 0 and the upper bound.
     *
     * @param upperBound Maximum number allowed.
     * @return index
     */
    public int generateRandomIndex(int upperBound) {
        Random randomizer = new Random();
        return randomizer.nextInt(upperBound);
    }

    /**
     * If client closes, remove said client from listeners.
     * @param client the client to remove.
     */
    public void removeClient(ServerThread client) {
        listeners.remove(client);
        removePlayer(client.getPlayer());

        // If client had keys in inventory, replace them on the map. (otherwise might not be able to finish the game)
        if (client.getPlayer().inventory.exists(new Item(Item.ItemType.FIRST_KEY, 1))) {
            placeKey(FIRSTKEY, firstKeyPosition[0], firstKeyPosition[1]);
        }
        if (client.getPlayer().inventory.exists(new Item(Item.ItemType.SECOND_KEY, 1))) {
            placeKey(SECONDKEY, secondKeyPosition[0], secondKeyPosition[1]);
        }
    }

    /**
     * Remove a player from the game board.
     * @param player The player to remove.
     */
    private void removePlayer(Player player) {
        int h = player.hPos;
        int w = player.wPos;
        gameMap[h][w] = CAVE;
    }
}
