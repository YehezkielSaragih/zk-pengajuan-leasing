package com.fif.zk.service;

import com.fif.zk.model.UserRole;

public interface UserRoleService {
    UserRole getRoleByName(String name);
    UserRole createRole(String name);
}
