package RogueServer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GameBoard {
    public int width = 50;
    public int height = 40;
    public String[][] gameMap;
    public String dirt = "#";
    public String cave = " ";
    public String door = "+";

    public String[][] cave1;

    public GameBoard() {
        this.gameMap = new String[height][width];
        for (String[] row : gameMap) {
            Arrays.fill(row, dirt);
        }
    }



    public void buildGameMap() {
        // Build all caves.
        addCave(3, 3, 7, 9);    // 7x9
        addCave(1, 19, 6, 12);  // Start cave 6x12
        addCave(3, 35, 8, 10);  // 8x10

        addCave(13, 10, 6, 19); // 6x19
        addCave(16, 36, 8, 12); // 8x12
        addCave(22, 20, 6, 5);  // Small cave 6x5

        addCave(25, 3, 11, 9);  // 11x9
        addCave(30, 20, 8, 10); // 8x10
        addCave(30, 36, 8, 12); // 8x12

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

    }

    /**
     * lägg in pengar och items.
     */
    public void populateMap() {
        // kasta ut allt.
    }

    /**
     * Place character on the map.
     * @param player
     */
    public void placePlayer(Player player) {
        gameMap[player.hPos][player.wPos] = player.getNameString();
    }

    /**
     * ändra position på gubbe i
     */
    public void movePlayer(Player player, int hOld, int wOld) {
        gameMap[hOld][wOld] = cave;
        placePlayer(player);
    }

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



    public void addCave(int rowPosition, int colPosition, int rowSize, int colSize) {
        for(int row = rowPosition; row < rowPosition+rowSize; row++) {
            for(int col = colPosition; col < colPosition+colSize; col++) {
                gameMap[row][col] = cave;
            }
        }
    }

    public void addHorizontalTunnel(int rowPosition, int colPosition, int length) {
        gameMap[rowPosition][colPosition-1] = door;
        for(int col = colPosition; col < colPosition+length; col++) {
            gameMap[rowPosition][col] = " ";
        }
        gameMap[rowPosition][colPosition+length-1] = door;
    }

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // public void addCaveToMap(String[] cave, String[][] map) {
    //     for
    // }

  //  public static void main(String args[]) {
  //      GameBoard myGame = new GameBoard();
  //      myGame.buildGameMap();
  //      myGame.printMap();
  //  }

    // public String[] cave1 = new String[5][5];
    // public String[] cave2 = new String[6][15];
    // public String[] cave3 = new String[6][15];
    // public String[] cave4 = new String[6][10];
    // public String[] cave5 = new String[7][20];
    // public String[] cave6 = new String[10][7];
    // public String[] cave7 = new String[5][5];
    // public String[] cave8 = new String[7][5];
    // public String[] cave9 = new String[5][5];


}
