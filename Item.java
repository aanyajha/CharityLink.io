import java.util.ArrayList;

public class Item {
    private ArrayList<String> hashtags;
    private String itemName;
    private State state;
    private int numItems;
    private String location;

    public Item(ArrayList<String> hashtags, String itemName, State state, int numItems, String location) {
        this.hashtags = hashtags;
        this.itemName = itemName;
        this.state = state;
        this.numItems = numItems;
        this.location = location;
    }

    public ArrayList<String> getHashtags() {
        return hashtags;
    }

    public String getItemName() {
        return itemName;
    }

    public State getState() {
        return state;
    }

    public int getNumItems() {
        return numItems;
    }

    public String getLocation() {
        return location;
    }

    public void setHashtags(ArrayList<String> hashtags) {
        this.hashtags = hashtags;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public enum State {
        REQUESTED,
        INSTOCK,
        INPROGRESS
    }
}
