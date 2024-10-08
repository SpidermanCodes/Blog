package com.example.Blog.controller;

import com.example.Blog.model.User;
import com.example.Blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1 Create User ---------------------> passed
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userService.usernameExists(user.getUsername())) {
            return ResponseEntity.badRequest().build();  
        }
        return ResponseEntity.ok(userService.createUser(user));
    }

    // 2 Get All Users -------------------> passed
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 3 Get User by Username ------------> passed
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4 Get User by ID ------------------> passed
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5 Update User by Username ---------> passed
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUserByUsername(@PathVariable String username, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok(userService.updateUserByUsername(username, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6 Update User by ID ---------------> passed
    @PutMapping("/id/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            return ResponseEntity.ok(userService.updateUserById(id, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 7 Delete User by Username ----------> passed
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }

    // 8 Delete User by ID ----------------> passed
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
