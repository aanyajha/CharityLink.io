package com.example.charitylink;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Integer zip;
    @Temporal(TemporalType.DATE)
    private Date joinDate;
    private Integer companyID;

    public User() {
    }

    public User(String name, String username, String password, String email, String addressLine1, String addressLine2,
                String city, String state, Integer zip, Date joinDate, Integer companyID) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.joinDate = joinDate;
        this.companyID = companyID;
    }

    public User(String name, String username, String password, String email, String addressLine1, String addressLine2,
                String city, String state, Integer zip, Date joinDate) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
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
}
