package com.iiitu.WomenSecurity.controller;

import com.iiitu.WomenSecurity.dto.EmergencyLocationDTO;
import com.iiitu.WomenSecurity.model.EmergencyLocation;
import com.iiitu.WomenSecurity.service.EmergencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping({"/api/emergency", "/emergency"})
public class EmergencyController {

    private final EmergencyService emergencyService;

    public EmergencyController(EmergencyService emergencyService){
        this.emergencyService = emergencyService;
    }

    @PostMapping({"", "/location"})
    public ResponseEntity<?> sendLocation(@Valid @RequestBody EmergencyLocationDTO locationDto,
                               Authentication authentication){

        Double latitude = locationDto.getLatitude();
        Double longitude = locationDto.getLongitude();

        if (locationDto.getCoordinates() != null) {
            if (latitude == null) latitude = locationDto.getCoordinates().getLat();
            if (longitude == null) longitude = locationDto.getCoordinates().getLng();
        }

        if (latitude == null || longitude == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Latitude and longitude are required. Send either latitude/longitude or coordinates.lat/coordinates.lng."
            ));
        }

        String userId = authentication != null && authentication.isAuthenticated()
                ? authentication.getName()
                : firstPresent(
                        locationDto.getEmail(),
                        locationDto.getReporterPhone(),
                        locationDto.getReporterName(),
                        "anonymous"
                );

        EmergencyLocation location = new EmergencyLocation(
                userId,
                locationDto.getStatus(),
                latitude,
                longitude,
                locationDto.getNote(),
                locationDto.getType(),
                locationDto.getPhoneNumber(),
                locationDto.getEmail()
        );
        
        // Map other fields manually
        if (locationDto.getPriority() != null) location.setPriority(locationDto.getPriority());
        if (locationDto.getCoordinates() != null) location.setCoordinates(locationDto.getCoordinates());
        if (locationDto.getLocation() != null) location.setLocation(locationDto.getLocation());
        if (locationDto.getDescription() != null) location.setDescription(locationDto.getDescription());
        if (locationDto.getVoiceTranscript() != null) location.setVoiceTranscript(locationDto.getVoiceTranscript());
        if (locationDto.getVoiceUrl() != null) location.setVoiceUrl(locationDto.getVoiceUrl());
        if (locationDto.getReporterName() != null) location.setReporterName(locationDto.getReporterName());
        if (locationDto.getReporterPhone() != null) location.setReporterPhone(locationDto.getReporterPhone());
        if (locationDto.getAssignedStationId() != null) location.setAssignedStationId(locationDto.getAssignedStationId());
        if (locationDto.getLinkedStationIds() != null) location.setLinkedStationIds(locationDto.getLinkedStationIds());
        if (locationDto.getEvidence() != null) location.setEvidence(locationDto.getEvidence());

        EmergencyLocation savedLocation = emergencyService.saveLocation(location);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    private String firstPresent(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return "anonymous";
    }
}
