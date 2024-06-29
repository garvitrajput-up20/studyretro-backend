package com.studyretro.controllers;

import com.studyretro.dto.LoginDto;
import com.studyretro.entity.Users;
import com.studyretro.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        String result = userService.login(loginDto);
        if ("Not Authenticated".equals(result)) {
            return ResponseEntity.status(401).body("Authentication Failed");
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/findAllUsers")
   // @PreAuthorize("hasRole('ADMIN')")
    public List<?> getAll(Users users){
        return userService.getUsers(users);
    }
}