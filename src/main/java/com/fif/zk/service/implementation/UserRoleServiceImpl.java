package com.fif.zk.service.implementation;

import com.fif.zk.model.UserRole;
import com.fif.zk.repository.UserRoleRepository;
import com.fif.zk.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getRoleByName(String name) {
        return userRoleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role not found: " + name));
    }

    @Override
    public UserRole createRole(String name) {
        UserRole userRole = new UserRole();
        userRole.setName(name);
        return userRoleRepository.save(userRole);
    }
}
