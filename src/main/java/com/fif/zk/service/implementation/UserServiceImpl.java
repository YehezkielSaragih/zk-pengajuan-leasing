package com.fif.zk.service.implementation;

import com.fif.zk.model.User;
import com.fif.zk.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();
    private List<User> users = new ArrayList<>();
    private int nextId = 1; // untuk auto-increment

    private UserServiceImpl(){}

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void addUser(User user){
        user.setId(nextId++);
        users.add(user);
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public boolean existsEmail(String email) {
        return users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }
}