package com.example.charitylink;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date joinDate;
    private Integer companyID;
    private Integer locationID;

    public User() {
    }

    public User(String name, String username, String password, String email, Date joinDate, Integer companyID, Integer locationID) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.companyID = companyID;
        this.locationID = locationID;
    }

    public User(String name, String username, String password, String email, Date joinDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.joinDate = joinDate;
        this.companyID = -1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }
}