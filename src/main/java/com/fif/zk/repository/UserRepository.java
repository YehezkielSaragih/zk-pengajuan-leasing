package com.fif.zk.repository;

import com.fif.zk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndDeletedAtIsNull(String email);
}
