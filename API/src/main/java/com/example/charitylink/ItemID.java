package com.example.charitylink;

import java.io.Serializable;

public class ItemID implements Serializable {
    private Integer userID;
    private Integer itemID;

    public ItemID(Integer userID, Integer itemID) {
        this.userID = userID;
        this.itemID = itemID;
    }
}
