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
}
