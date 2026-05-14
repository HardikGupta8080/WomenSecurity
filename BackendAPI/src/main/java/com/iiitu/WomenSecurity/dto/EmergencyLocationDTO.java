package com.iiitu.WomenSecurity.dto;

import com.iiitu.WomenSecurity.model.Coordinates;
import java.util.List;

public class EmergencyLocationDTO {

    private String status;
    private String priority;

    private Double latitude;

    private Double longitude;

    private Coordinates coordinates;
    private String location;
    private String description;
    private String voiceTranscript;
    private String voiceUrl;
    private String reporterName;
    private String reporterPhone;
    private String assignedStationId;
    private List<String> linkedStationIds;
    private List<String> evidence;
    private String note;
    private String type;
    private String phoneNumber;
    private String email;

    // Getters and Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getVoiceTranscript() { return voiceTranscript; }
    public void setVoiceTranscript(String voiceTranscript) { this.voiceTranscript = voiceTranscript; }

    public String getVoiceUrl() { return voiceUrl; }
    public void setVoiceUrl(String voiceUrl) { this.voiceUrl = voiceUrl; }

    public String getReporterName() { return reporterName; }
    public void setReporterName(String reporterName) { this.reporterName = reporterName; }

    public String getReporterPhone() { return reporterPhone; }
    public void setReporterPhone(String reporterPhone) { this.reporterPhone = reporterPhone; }

    public String getAssignedStationId() { return assignedStationId; }
    public void setAssignedStationId(String assignedStationId) { this.assignedStationId = assignedStationId; }

    public List<String> getLinkedStationIds() { return linkedStationIds; }
    public void setLinkedStationIds(List<String> linkedStationIds) { this.linkedStationIds = linkedStationIds; }

    public List<String> getEvidence() { return evidence; }
    public void setEvidence(List<String> evidence) { this.evidence = evidence; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
