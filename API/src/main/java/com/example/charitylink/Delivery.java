package com.example.charitylink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Delivery {

    @Id
    @GeneratedValue
    private Integer id;
    private Integer donator;
    private Integer requesterID;
    private String status;

    public Delivery() {}
    
    public Delivery(Integer donator, Integer requesterID, String status) {
        this.donator = donator;
        this.requesterID = requesterID;
        this.status = status;
    }

    public Integer getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(Integer requesterID) {
        this.requesterID = requesterID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDonator() {
        return donator;
    }

    public void setDonator(Integer donator) {
        this.donator = donator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}

