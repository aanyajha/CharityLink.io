package com.example.charitylink;

import jakarta.persistence.*;
import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;

@Entity
public class Location {
    @Id
    @GeneratedValue
    private Integer id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Integer zip;
    private Double latitude;
    private Double longitude;

    public Location(Integer id, String addressLine1, String addressLine2, String city, String state, Integer zip) {
        this.id = id;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("8ea9eae59cbf470b90604e16c30b8a0e");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(this.addressLine1 + ", " + this.city + ", " + this.state + ", " + this.zip + ", USA");
        request.setRestrictToCountryCode("us"); // restrict results to a specific country
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();
        this.latitude = firstResultLatLng.getLat();
        this.longitude = firstResultLatLng.getLng();
    }

    public Location(String addressLine1, String addressLine2, String city, String state, Integer zip) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("8ea9eae59cbf470b90604e16c30b8a0e");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(this.addressLine1 + ", " + this.city + ", " + this.state + ", " + this.zip + ", USA");
        request.setRestrictToCountryCode("us"); // restrict results to a specific country
        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();
        this.latitude = firstResultLatLng.getLat();
        this.longitude = firstResultLatLng.getLng();
    }

    public Location() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}