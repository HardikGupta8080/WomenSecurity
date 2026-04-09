package com.iiitu.WomenSecurity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "emergency_locations")
public class EmergencyLocation {

    @Id
    private String id;

    private String userId;
    private String status;
    private double latitude;
    private double longitude;
    private String note;
    private String type;
    private String phoneNumber;
    private String email;

    private LocalDateTime time;

    public EmergencyLocation(){}

    public EmergencyLocation(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = LocalDateTime.now();
    }

    public EmergencyLocation(
            String userId,
            String status,
            double latitude,
            double longitude,
            String note,
            String type,
            String phoneNumber,
            String email
    ) {
        this.userId = userId;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.time = LocalDateTime.now();
    }

    public String getId() { return id; }

    public String getUserId() { return userId; }

    public String getStatus() { return status; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public String getNote() { return note; }

    public String getType() { return type; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getEmail() { return email; }

    public LocalDateTime getTime() { return time; }

    public void setUserId(String userId) { this.userId = userId; }

    public void setStatus(String status) { this.status = status; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setNote(String note) { this.note = note; }

    public void setType(String type) { this.type = type; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setEmail(String email) { this.email = email; }
}
