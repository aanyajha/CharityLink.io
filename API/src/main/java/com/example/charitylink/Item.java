package com.example.charitylink;

import jakarta.persistence.*;

@Entity
@IdClass(ItemID.class)
public class Item {
    @Id
    private Integer userID;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer itemID;
    private String state;
    private Integer numItems;
    private String hashtags;
    private String location;

    public Item(Integer userID, String state, Integer numItems, String hashtags, String location) {
        this.userID = userID;
        this.state = state;
        this.numItems = numItems;
        this.hashtags = hashtags;
        this.location = location;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNumItems() {
        return numItems;
    }

    public void setNumItems(Integer numItems) {
        this.numItems = numItems;
    }

    public String getHashtags() {
        return hashtags;
    }

    public void setHashtags(String hashtags) {
        this.hashtags = hashtags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
