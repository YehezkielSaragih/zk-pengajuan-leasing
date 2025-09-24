package com.fif.zk.repository;

import com.fif.zk.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByCreditorIdAndDeletedAtIsNull(Integer creditorId);
    List<Loan> findByDeletedAtIsNull();
}