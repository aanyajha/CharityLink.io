package com.example.charitylink;

//Imports

public class GeocodingTest {
    JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("8ea9eae59cbf470b90604e16c30b8a0e");
    JOpenCageForwardRequest request = new JOpenCageForwardRequest("375 Albert Rd, Woodstock, Cape Town, 7915, South Africa");
request.setRestrictToCountryCode("za"); // restrict results to a specific country
request.setBounds(18.367, -34.109, 18.770, -33.704); // restrict results to a geographic bounding box (southWestLng, southWestLat, northEastLng, northEastLat)

    JOpenCageResponse response = jOpenCageGeocoder.forward(request);
    JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
System.out.println(firstResultLatLng.getLat().toString() + "," + firstResultLatLng.getLng().toString());
}
