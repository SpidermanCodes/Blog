package com.example.Blog.service;

import com.example.Blog.model.User;
import com.example.Blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUserByUsername(String username, User userDetails) {
        return userRepository.findByUsername(username).map(user -> {
            user.setUsername(userDetails.getUsername()); // May keep the same username or change
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserById(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUserByUsername(String username) {
        userRepository.findByUsername(username).ifPresent(userRepository::delete);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
