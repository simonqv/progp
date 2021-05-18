package RogueServer;


public class Item {
    enum ItemType { NET, FIRSTKEY, SECONDKEY, COINS }

    ItemType item;
    int quantity;

    public Item(ItemType item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Increases the quantity of coins.
     */
    public void pickupCoin() {
        if (item == ItemType.COINS) {
            quantity++;
        } else {
            System.out.println("Error, not coin");
        }
    }

    public ItemType getItem() {
        return item;
    }

    public int getQuantity() {return quantity;}


    /**
     *
     * @param other the item to check.
     * @return True if ItemType is the same.
     */
    public boolean equals(Item other) {
        return other.getItem() == this.getItem();
    }

    /**
     *
     * @return The string representation of the item. Like: #.ItemType
     */
    public String toString() {
        return quantity + "." + item;
    }

}
