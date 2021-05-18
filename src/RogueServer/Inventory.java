package RogueServer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Item> inventory;

    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    /**
     * Adds item to the players inventory.
     * @param item to add.
     */
    public void addItem(Item item) {
        if (item.getItem() == Item.ItemType.COINS) {
            if (inventory.contains(item)) {
                int ind = inventory.indexOf(item);
                inventory.get(ind).pickupCoin();
            } else {
                inventory.add(item);
            }
        } else if (item.getItem() == Item.ItemType.FIRSTKEY || item.getItem() == Item.ItemType.SECONDKEY) {
            inventory.add(item);
        } else {
            System.out.println("Something went wrong in the pickup...");
        }
    }

    /**
     * The string representation of the inventory to send to client.
     * Format: comma-seperated list of key-value pairs. Like: #.item,#.item,#.item
     */
    public byte[] byteInventory() {
        StringBuilder sb = new StringBuilder();
        for (Item item : inventory) {
            sb.append(item.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        // 1.FIRSTKEY,4.COINS
        byte[] byteInv = sb.toString().getBytes(StandardCharsets.US_ASCII); // len = 4
        byte[] b = new byte[sb.length() + 2]; // len 6
        b[0] = 2;
        System.arraycopy(byteInv, 0, b, 1, byteInv.length);
        b[b.length - 1] = 0;
        return b;
    }
}