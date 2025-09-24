package com.fif.zk.service.implementation;

import com.fif.zk.model.User;
import com.fif.zk.repository.UserRepository;
import com.fif.zk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user); // JPA save
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll(); // JPA findAll
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElse(null); // pakai Optional dari repo
    }

    @Override
    public boolean validateUser(String email, String password) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .map(u -> u.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email); // JPA exists
    }
}