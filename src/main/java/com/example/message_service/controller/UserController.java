package com.example.message_service.controller;

import com.example.message_service.entity.User;
import com.example.message_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @GetMapping("/admins")
    public ResponseEntity<List<User>> getAllAdmins() {
        return ResponseEntity.ok(userService.findAllAdmins());
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        return ResponseEntity.ok(userService.findByUserName(userName));
    }
}
