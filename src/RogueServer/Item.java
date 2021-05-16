package RogueServer;


public class Item {
    enum itemType { NET, FIRSTKEY, SECONDKEY, COINS }

    itemType item;
    int quantity;

    public Item(itemType item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public void pickupCoin(int num) {
        if (item == itemType.COINS) {
            quantity += num;
        } else {
            System.out.println("Error, not coin");
        }
    }


}
