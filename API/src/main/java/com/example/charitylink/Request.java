package com.example.charitylink;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Request {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer requestor;
    private Integer itemID;
    private Integer quantity;
    private String deliveryType;
    private Boolean delivered;
    @Transient
    private String hashtags;
    @Transient
    private String name;
    @Transient
    private Double distance;
    @Transient
    private String img;
    @Transient
    private Integer location;

    public Request() {};
    public Request(Integer requestor, Integer itemID, Integer quantity, String deliveryType, Boolean delivered) {
        this.requestor = requestor;
        this.itemID = itemID;
        this.quantity = quantity;
        this.deliveryType = deliveryType;
        this.delivered = delivered;
    }

    public Request(Integer requestor, Integer itemID, Integer quantity, String deliveryType, Boolean delivered, String hashtags, String name, Double distance, String img, Integer location) {
        this.requestor = requestor;
        this.itemID = itemID;
        this.quantity = quantity;
        this.deliveryType = deliveryType;
        this.hashtags = hashtags;
        this.name = name;
        this.distance = distance;
        this.img = img;
        this.location = location;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Integer getLocation() {
        return location;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setLocation(Integer location) {
        this.location = location;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestor() {
        return requestor;
    }

    public void setRequestor(Integer requestor) {
        this.requestor = requestor;
    }

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
