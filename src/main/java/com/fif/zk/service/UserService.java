package com.fif.zk.service;

import com.fif.zk.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService instance = new UserService();
    private List<User> users = new ArrayList<>();
    private int nextId = 1; // untuk auto-increment

    private UserService(){}

    public static UserService getInstance() { return instance; }

    public void addUser(User user){
        user.setId(nextId++);
        users.add(user);
    }

    // Get all users
    public List<User> getUsers() {
        return users;
    }

    // Find user by email
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    // Validate login
    public boolean validateUser(String email, String password) {
        User user = getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}
