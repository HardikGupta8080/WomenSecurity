package com.iiitu.WomenSecurity.service;

import com.iiitu.WomenSecurity.model.EmergencyLocation;
import com.iiitu.WomenSecurity.repository.EmergencyRepository;
import org.springframework.stereotype.Service;

@Service
public class EmergencyService {

    private final EmergencyRepository emergencyRepository;

    public EmergencyService(EmergencyRepository emergencyRepository){
        this.emergencyRepository = emergencyRepository;
    }

    public EmergencyLocation saveLocation(EmergencyLocation location){
        return emergencyRepository.save(location);
    }
}