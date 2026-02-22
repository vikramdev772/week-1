package com.example.myapp.controller;

import com.example.myapp.DTO.LoginRequest;
import com.example.myapp.DTO.SignupRequest;
import com.example.myapp.model.User;
import com.example.myapp.repo.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.myapp.security.JwtService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository db;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignupRequest sd) {

        
        if (db.findByEmail(sd.getEmail()).isPresent()) {
            return "Email already registered";
        }

        User user = new User();
        user.setName(sd.getName());
        user.setEmail(sd.getEmail());
        user.setPassword(passwordEncoder.encode(sd.getPassword()));
        db.save(user);

        return "Signup successful";
    }

    @PostMapping("/login")
    public Map<String, Object> loginApi(@RequestBody LoginRequest loginData) {

        Optional<User> optionalUser = db.findByEmail(loginData.getEmail());

        if (optionalUser.isEmpty()) {
            Map<String, Object> res = new HashMap<>();
            res.put("success", false);
            res.put("message", "User not found");
            return res;
        }

        User user = optionalUser.get();

        boolean matches = passwordEncoder.matches(loginData.getPassword(), user.getPassword())
                || user.getPassword().equals(loginData.getPassword());
        if (!matches) {
            Map<String, Object> res = new HashMap<>();
            res.put("success", false);
            res.put("message", "Invalid password");
            return res;
        }

        String token = jwtService.generateToken(user.getEmail());
        Map<String, Object> res = new HashMap<>();
        res.put("success", true);
        res.put("message", "Login successful");
        res.put("token", token);
        return res;
    }

    
}
