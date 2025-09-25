package com.fif.zk.service.implementation;

import com.fif.zk.model.User;
import com.fif.zk.repository.UserRepository;
import com.fif.zk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

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
                .orElse(null);
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