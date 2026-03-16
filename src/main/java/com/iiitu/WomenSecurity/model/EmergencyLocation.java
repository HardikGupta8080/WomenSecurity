package com.iiitu.WomenSecurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "emergency_locations")
public class EmergencyLocation {

    @Id
    private String id;

    private String userId;
    private double latitude;
    private double longitude;

    private LocalDateTime time;

    public EmergencyLocation(){}

    public EmergencyLocation(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getUserId() { return userId; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public LocalDateTime getTime() { return time; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }
}
