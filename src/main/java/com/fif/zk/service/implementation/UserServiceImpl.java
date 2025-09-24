package com.fif.zk.service.implementation;

import com.fif.zk.model.User;
import com.fif.zk.repository.UserRepository;
import com.fif.zk.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance = new UserServiceImpl();
    private UserRepository userRepository = UserRepository.getInstance();

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void addUser(User user) {
        // createdAt and updatedAt are set in User constructor
        userRepository.addUser(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public boolean validateUser(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) return false;
        return user.getPassword().equals(password); // ideally use hashed password comparison
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsEmail(email);
    }
}