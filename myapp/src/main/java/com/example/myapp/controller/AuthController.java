package com.example.myapp.controller;

import com.example.myapp.DTO.LoginRequest;
import com.example.myapp.DTO.SignupRequest;
import com.example.myapp.model.User;
import com.example.myapp.repo.UserRepository;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository db;

    @PostMapping("/signup")
    public String signUp(@RequestBody SignupRequest sd) {

        if (db.findByEmail(sd.getEmail()).isPresent()) {
            return "Email already registered";
        }

        User user = new User();
        user.setName(sd.getName());
        user.setEmail(sd.getEmail());
        user.setPassword(sd.getPassword());
        db.save(user);

        return "Signup successful";
    }

    @PostMapping("/login")
    public String loginApi(@RequestBody LoginRequest loginData) {

        Optional<User> optionalUser = db.findByEmail(loginData.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(loginData.getPassword())) {
            return "Invalid password";
        }

        return "Login successful";
    }

    @PutMapping("/user/{id}")
    public Map<String, Object> updateUser(
            @PathVariable Long id,
            @RequestBody SignupRequest obj) {

        Map<String, Object> res = new HashMap<>();

        Optional<User> optionalUser = db.findById(id);

        if (optionalUser.isEmpty()) {
            res.put("success", false);
            res.put("message", "User not found");
            return res;
        }

        User user = optionalUser.get();

        user.setName(obj.getName());
        user.setEmail(obj.getEmail());

        db.save(user);

        res.put("success", true);
        res.put("message", "User updated successfully");
        res.put("name", user.getName());
        res.put("email", user.getEmail());

        return res;
    }

}
