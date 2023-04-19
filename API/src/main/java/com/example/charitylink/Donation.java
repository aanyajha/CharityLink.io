package com.example.charitylink;

import jakarta.persistence.*;

@Entity
public class Donation {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer itemID;
    private Integer requester;
    private Integer donator;
    private Integer quantity;
    private String state;

    public Donation() {};
    public Donation(Integer itemID, Integer requester, Integer donator, Integer quantity, String state) {
        this.itemID = itemID;
        this.requester = requester;
        this.donator = donator;
        this.quantity = quantity;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getRequester() {
        return requester;
    }

    public void setRequester(Integer requester) {
        this.requester = requester;
    }

    public Integer getDonator() {
        return donator;
    }

    public void setDonator(Integer donator) {
        this.donator = donator;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
