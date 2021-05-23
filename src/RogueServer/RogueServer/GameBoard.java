package RogueServer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import RogueServer.Item.ItemType;

public class GameBoard {
    public int width = 50;
    public int height = 40;
    public char[][] gameMap;

    public static final char DIRT = '#';
    public static final char CAVE = ' ';
    public static final char DOOR = '+';
    public static final char COIN = '*';
    public static final char BUTTON = 'B';

    public static final char FIRSTKEY = '?';
    public static final char SECONDKEY = '!';

    int[] firstKeyPosition = new int[]{26,22};
    int[] secondKeyPosition = new int[]{22,46};
    int[] buttonDoor = new int[]{11,40};
    int[] firstKeyDoor = new int[]{5,18};
    int[] secondKeyDoor = new int[]{38,24};

    int[][] doorPositions = new int[][]{buttonDoor, firstKeyDoor, secondKeyDoor}; // 3 doors...
    public int numberOfCoins = 50;  

    private final List<GameBoardListener> listeners;

    /**
     * Create a new game board.
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

    public void addDoors() {
        for(int[] doorPosition : doorPositions) {
            gameMap[doorPosition[0]][doorPosition[1]] = DOOR;
            if (doorPosition[0] == buttonDoor[0] && doorPosition[1] == buttonDoor[1]){
                gameMap[doorPosition[0]-1][doorPosition[1]-1] = BUTTON;
                gameMap[doorPosition[0]-1][doorPosition[1]+1] = BUTTON;
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
      * @param player the player that's going to move.
      */
     public void placePlayer(Player player) {
         gameMap[player.hPos][player.wPos] = player.getNameString();
        
         if (buttonsArePressed()) {
            // Unlock button-door
            unlockDoor(buttonDoor);
         } else if(player.inventory.exists(new Item(ItemType.FIRST_KEY, 0)) && player.hPos == firstKeyDoor[0] && player.wPos == firstKeyDoor[1]+1){
            // Unlock door with the first key
            unlockDoor(firstKeyDoor);
         } else if(player.inventory.exists(new Item(ItemType.SECOND_KEY, 0)) && player.hPos == secondKeyDoor[0]-1 && player.wPos == secondKeyDoor[1]){
            // Unlock door with the second key
            unlockDoor(secondKeyDoor);
            // It is a win!
            listeners.forEach(GameBoardListener::boardChanged);
            listeners.forEach(GameBoardListener::winner);
         }
         // Notify all listeners of the changed game board
         listeners.forEach(GameBoardListener::boardChanged);
     }

     /**
      * Change the players position on board.
      */
     public void movePlayer(Player player, int hOld, int wOld) {
         // Set old position to empty.
         if (hOld == buttonDoor[0]-1 && wOld == buttonDoor[1]-1 || hOld == buttonDoor[0]-1 && wOld == buttonDoor[1]+1){
            gameMap[hOld][wOld] = BUTTON;
         }
         else{
            gameMap[hOld][wOld] = CAVE;
         }        
         // Place the player on the next position.
         placePlayer(player);
     }

     public boolean buttonsArePressed() {
         return gameMap[buttonDoor[0]-1][buttonDoor[1]-1] != BUTTON && gameMap[buttonDoor[0]-1][buttonDoor[1]+1] != BUTTON;
     }

     public void unlockDoor(int[] door) {
        gameMap[door[0]][door[1]] = CAVE;
     }


    public byte[] toByte() {
        // convert to byte!
        int k = 0;
        byte[] byteBoard = new byte[height * width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                byteBoard[k] = (byte) gameMap[i][j];
                k ++;
            }
        }
        return byteBoard;
    }

    /**
     * Draw cave on game board based on specified coordinates and cave size.
     * @param rowPosition
     * @param colPosition
     * @param rowSize
     * @param colSize
     */
    public void addCave(int rowPosition, int colPosition, int rowSize, int colSize, char caveSymbol) {
        for(int row = rowPosition; row < rowPosition+rowSize; row++) {
            for(int col = colPosition; col < colPosition+colSize; col++) {
                gameMap[row][col] = caveSymbol;
            }
        }
    }

    /**
     * Draw a horizontal tunnel on game board.
     * @param rowPosition
     * @param colPosition
     * @param length
     */
    public void addHorizontalTunnel(int rowPosition, int colPosition, int length) {
        for(int col = colPosition-1; col < colPosition+length; col++) {
            gameMap[rowPosition][col] = CAVE;
        }
    }

    /**
     * Draw a vertical tunnel on game board.
     * @param rowPosition
     * @param colPosition
     * @param length
     */
    public void addVerticalTunnel(int rowPosition, int colPosition, int length) {
        for(int row = rowPosition-1; row < rowPosition+length; row++) {
            gameMap[row][colPosition] = CAVE;
        }
    }

    public void printMap() {
        for(char[] row : gameMap) {
            for (char c : row) {
                System.out.print(c);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Get the witdth of board.
     * @return width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of board.
     * @return height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Place all coins on game board.
     * @param numberOfCoins
     */
    public void placeCoins(int numberOfCoins){
        int num = numberOfCoins;
        int row;
        int col;
        while(num != 0) {
            row = generateRandomIndex(getHeight());
            col = generateRandomIndex(getWidth());
            if(gameMap[row][col] == CAVE) {
                gameMap[row][col] = COIN;
                num--;
            }
        }
    }

    /**
     * Add a key to game board.
     * @param key
     * @param row
     * @param col
     */
    public void placeKey(char key, int row, int col) {
        gameMap[row][col] = key;
    }

    /**
     * Generates a random index between lower bound 0 and the upper bound.
     * @param upperBound
     * @return index
     */
    public int generateRandomIndex(int upperBound) {
        Random randomizer = new Random();
        int index = randomizer.nextInt(upperBound);
        return index;
    }

}
