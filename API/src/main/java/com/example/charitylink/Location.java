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
        try {
            this.latitude = firstResultLatLng.getLat();
            this.longitude = firstResultLatLng.getLng();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            this.latitude = firstResultLatLng.getLat();
            this.longitude = firstResultLatLng.getLng();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
    }

    public Double findDistance(Double latitude, Double longitude) {
        // Distance distance
        double r = 3958.8;
        double latDistance = Math.toRadians(this.latitude - latitude);
        double lonDistance = Math.toRadians(this.longitude - longitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(this.latitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = r * c;
        return distance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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