package com.studyretro.controllers;

import com.studyretro.entity.Users;
import com.studyretro.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        Users registerUser = userService.registerUser(user);
        return ResponseEntity.ok().body("User Registered");
    }

    @GetMapping("/findall")
   // @PreAuthorize("hasRole('ADMIN')")
    public List<?> getAll(Users users){
        return userService.getUsers(users);

    }

}