package com.iiitu.WomenSecurity.repository;

import com.iiitu.WomenSecurity.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByUsername(String username);

}