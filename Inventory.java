import java.util.ArrayList;

public class Inventory {
    private long inventoryID;
    private ArrayList<String> items;
    private int totalItems;

    public Inventory(long inventoryID) {
        this.inventoryID = inventoryID;
        this.items = new ArrayList<String>();
        this.totalItems = 0;
    }

    public long getInventoryID() {
        return inventoryID;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void addItem(String item) {
        items.add(item);
        totalItems++;
    }

    public void removeItem(String item) {
        items.remove(item);
        totalItems--;
    }
}
