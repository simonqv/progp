package RogueCommon;

public class CommandConstants {
    // Bidirectional
    public static final byte CONNECT = 0;

    // From server to client
    public static final byte BOARD = 10;
    public static final byte INVENTORY = 11;
    public static final byte MESSAGE = 12;

    // From client to server
    public static final byte ACTION = 20;
}
