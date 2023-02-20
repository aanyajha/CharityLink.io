package com.example.charitylink;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ItemID implements Serializable {
    private Integer userID;
    private Integer itemID;

    public ItemID(Integer userID, Integer itemID) {
        this.userID = userID;
        this.itemID = itemID;
    }

    public ItemID() {

    }
}
