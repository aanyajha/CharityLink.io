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
    private Integer requestID;
    private String status;
//    private boolean delivered;
    private String eta;

    public Delivery() {}

    public Delivery(Integer donator, Integer requestID, String status/*, boolean delivered*/, String eta) {
        this.donator = donator;
        this.requestID = requestID;
        this.status = status;
//        this.delivered = delivered;
        this.eta = eta;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requesterID) {
        this.requestID = requesterID;
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

//    public boolean getDelivered() {
//        return delivered;
//    }
//
//    public void setDelivered(boolean delivered) {
//        this.delivered = delivered;
//    }
}


