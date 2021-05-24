package RogueServer;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds item to the players inventory.
     * @param item to add.
     */
    public void addItem(Item item) {
        if (item.getItem() == Item.ItemType.COINS) {
            if (exists(item)) {
                item.increaseQuantity();
            } else {
                items.add(item);
            }

        } else if (item.getItem() == Item.ItemType.FIRST_KEY || item.getItem() == Item.ItemType.SECOND_KEY) {
            items.add(item);
        } else {
            System.out.println("Something went wrong in the pickup...");
        }
    }

    public boolean exists(Item item) {
        return items.stream().anyMatch(o -> o.getItem().equals(item.getItem()));
    }

    public List<Item> getItems() {
        return items;
    }


}
