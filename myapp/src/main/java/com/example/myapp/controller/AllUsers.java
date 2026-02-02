package com.example.myapp.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.model.User;
import com.example.myapp.repo.UserRepository;

@RestController
public class AllUsers {

    @Autowired
    UserRepository db;

    @GetMapping("/all")
        public  List<User> getAll(){
            return db.findAll();
        }
}
