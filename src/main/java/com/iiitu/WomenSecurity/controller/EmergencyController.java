package com.iiitu.WomenSecurity.controller;

import com.iiitu.WomenSecurity.model.EmergencyLocation;
import com.iiitu.WomenSecurity.service.EmergencyService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    private final EmergencyService emergencyService;

    public EmergencyController(EmergencyService emergencyService){
        this.emergencyService = emergencyService;
    }

    @PostMapping("/location")
    public String sendLocation(@RequestBody EmergencyLocation location,
                               Authentication authentication){

        String userEmail = authentication.getName(); // from JWT

        location.setUserId(userEmail);

        emergencyService.saveLocation(location);

        return "Emergency location saved";
    }
}