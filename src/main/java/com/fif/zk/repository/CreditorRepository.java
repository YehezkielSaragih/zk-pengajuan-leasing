package com.fif.zk.repository;

import com.fif.zk.model.Creditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditorRepository extends JpaRepository<Creditor, Integer> {
    List<Creditor> findByDeletedAtIsNull();
}