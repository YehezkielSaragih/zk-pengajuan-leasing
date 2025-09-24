package com.fif.zk.service;

import com.fif.zk.model.User;

import java.util.List;

public interface UserService {

    void addUser(User user);
    List<User> getUsers();
    User getUserByEmail(String email);
    boolean validateUser(String email, String password);
    boolean existsEmail(String email);
}
