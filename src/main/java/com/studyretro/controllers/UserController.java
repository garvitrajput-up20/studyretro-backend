package com.studyretro.controllers;

import com.studyretro.entity.Users;
import com.studyretro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(Users user) {
        Users registerUser = userService.registerUser(user);
        return ResponseEntity.ok().body("User Registered");
    }

    @GetMapping("/findall")
    public List<Users> getAll(Users users){
        return userService.getUsers(users);
    }

}