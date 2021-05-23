package RogueServer;
// Simon Larspers Qvist
// Beata Johansson
// INET 2021

public class Item {
    enum ItemType { FIRST_KEY, SECOND_KEY, COINS }

    ItemType item;
    int quantity;

    public Item(ItemType item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Increases the quantity of the item. (In this case, coins)
     */
    public void increaseQuantity() {
        quantity++;
    }

    public ItemType getItem() {
        return item;
    }

    public int getQuantity() {return quantity;}

    /**
     *
     * @return The string representation of the item. Like: #.ItemType
     */
    public String toString() {
        return quantity + "." + item;
    }

}
