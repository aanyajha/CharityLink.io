package com.example.charitylink;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String description;
    private Integer locationID;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Integer companyId;
    private String userList;
    private String ownerList;
    @Transient
    private Double distance;

    public Event(String title, String description, Integer locationID, Date date, Integer companyId, String userList, String ownerList) {
        this.title = title;
        this.description = description;
        this.locationID = locationID;
        this.date = date;
        this.companyId = companyId;
        this.userList = userList;
        this.ownerList = ownerList;
    }

    public Event(Integer id, String title, String description, Integer locationID, Date date, Integer companyId, String userList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.locationID = locationID;
        this.date = date;
        this.companyId = companyId;
        this.userList = userList;
    }

    public Event() {

    }

    public String getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(String ownerList) {
        this.ownerList = ownerList;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getUserList() {
        return userList;
    }

    public void setUserList(String userList) {
        this.userList = userList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}