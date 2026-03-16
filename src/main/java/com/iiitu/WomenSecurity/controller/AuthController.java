package com.iiitu.WomenSecurity.controller;



import com.iiitu.WomenSecurity.jwt.JwtService;
import com.iiitu.WomenSecurity.model.User;
import com.iiitu.WomenSecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repository,
                          JwtService jwtService,
                          PasswordEncoder encoder){
        this.repository=repository;
        this.jwtService=jwtService;
        this.encoder=encoder;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user){

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);

        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){

        User dbUser = repository.findByUsername(user.getUsername()).orElseThrow();

        if(encoder.matches(user.getPassword(),dbUser.getPassword())){
            return jwtService.generateToken(user.getUsername());
        }

        return "Invalid credentials";
    }

}
