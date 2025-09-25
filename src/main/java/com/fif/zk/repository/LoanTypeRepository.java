package com.fif.zk.repository;

import com.fif.zk.model.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType, Integer> {
}
