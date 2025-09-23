package com.fif.zk.service;

import com.fif.zk.model.Loan;

import java.util.List;

public interface LoanService {
    List<Loan> getLoans();
    void addLoan(Loan loan);
    void deleteLoan(int id);
    Loan getLoanById(int id);
    void updateLoan(Loan loan);
}
