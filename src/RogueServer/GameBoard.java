package RogueServer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import javax.print.attribute.standard.NumberOfDocuments;

public class GameBoard {
    public int width = 50;
    public int height = 40;
    public String[][] gameMap;
    public String dirt = "#";
    public String cave = " ";
    public String door = "+";
    public String coin = "*";

    public int numberOfCoins = 50;     

    public GameBoard() {
        this.gameMap = new String[height][width];
        for (String[] row : gameMap) {
            Arrays.fill(row, dirt);
        }
    }



    public void buildGameMap(String caveSymbol, String dirtSymbol) {
        // Build all caves.
        addCave(3, 3, 7, 9, caveSymbol);    // 7x9
        addCave(1, 19, 6, 12, caveSymbol);  // Start cave 6x12
        addCave(3, 35, 8, 10, caveSymbol);  // 8x10

        addCave(13, 10, 6, 19, caveSymbol); // 6x19
        addCave(16, 36, 8, 12, caveSymbol); // 8x12
        addCave(22, 20, 6, 5, caveSymbol);  // Small cave 6x5

        addCave(25, 3, 11, 9, caveSymbol);  // 11x9
        addCave(30, 20, 8, 10, caveSymbol); // 8x10
        addCave(30, 36, 8, 12, caveSymbol); // 8x12

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

        // Draw a wall in the cave to the right.
        addCave(19, 36, 1, 12, dirtSymbol);
    }

    /**
     * Place items on game board.
     */
    public void populateMap(int numberOfCoins) {
        placeCoins(numberOfCoins);
    }

   
    // /**
    //  * Place character on the map.
    //  * @param player
    //  */
    // public void placePlayer(Player player) {
    //     gameMap[player.hPos][player.wPos] = player.getNameString();
    // }

    // /**
    //  * Change the players position on board.
    //  */
    // public void movePlayer(Player player, int hOld, int wOld) {
    //     gameMap[hOld][wOld] = cave;
    //     placePlayer(player);
    // } 


    public byte[] toByte() {
        // convert to byte!!
        int k = 1;
        byte[] byteBoard = new byte[height * width + 1];
        byteBoard[0] = 1;
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                byte[] b = gameMap[i][j].getBytes(StandardCharsets.US_ASCII);
                byteBoard[k] = b[0];
                k ++;
            }
        }
        return byteBoard;
    }

    /**
     *
     */
    public void defeatMonster() {
        // Remove monster!
    }

    /**
     * Draw cave on game board based on specified coordinates and cave size.
     * @param rowPosition
     * @param colPosition
     * @param rowSize
     * @param colSize
     */
    public void addCave(int rowPosition, int colPosition, int rowSize, int colSize, String caveSymbol) {
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
        gameMap[rowPosition][colPosition-1] = door;
        for(int col = colPosition; col < colPosition+length; col++) {
            gameMap[rowPosition][col] = " ";
        }
        gameMap[rowPosition][colPosition+length-1] = door;
    }

    /**
     * Draw a vertical tunnel on game board.
     * @param rowPosition
     * @param colPosition
     * @param length
     */
    public void addVerticalTunnel(int rowPosition, int colPosition, int length) {
        gameMap[rowPosition-1][colPosition] = door;
        for(int row = rowPosition; row < rowPosition+length; row++) {
            gameMap[row][colPosition] = " ";
        }
        gameMap[rowPosition+length-1][colPosition] = door;
    }

    public void printMap() {
        for(String[] row : gameMap) {
            for( int i = 0; i < row.length; i++) {
                System.out.print(row[i]);
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
            if(gameMap[row][col] == " ") {
                gameMap[row][col] = coin;
                num--;
            }
        }
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


    public static void main(String args[]) {
        GameBoard mygame = new GameBoard();
        mygame.buildGameMap(mygame.cave, mygame.dirt);
        mygame.populateMap(mygame.numberOfCoins);
        mygame.printMap();
    }

}
