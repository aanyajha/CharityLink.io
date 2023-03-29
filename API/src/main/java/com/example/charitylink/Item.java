package com.example.charitylink;

import jakarta.persistence.*;

@Entity
@IdClass(ItemID.class)
public class Item {
    @Id
    private Integer userID;
    @Id
    private Integer itemID;
    private String name;
    private String state;
    private Integer numItems;
    private String hashtags;
    private Integer location;
    private String img;

    public Item(Integer userID, String name, String state, Integer numItems, String hashtags, Integer location, String img) {
        this.userID = userID;
        this.name = name;
        this.state = state;
        this.numItems = numItems;
        this.hashtags = hashtags;
        this.location = location;
        this.img = img;
    }

    public Item(Integer userID, Integer itemID, String name, String state, Integer numItems, String hashtags, Integer location, String img) {
        this.userID = userID;
        this.itemID = itemID;
        this.name = name;
        this.state = state;
        this.numItems = numItems;
        this.hashtags = hashtags;
        this.location = location;
        this.img = img;
    }

    public Item() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
}