package com.iiitu.WomenSecurity.repository;

import com.iiitu.WomenSecurity.model.EmergencyLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmergencyRepository extends MongoRepository<EmergencyLocation, String> {
}