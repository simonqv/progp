package RogueServer;

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
    public String stringInventory() {
        StringBuilder sb = new StringBuilder();
        for (Item item : inventory) {
            sb.append(item.toString()).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}