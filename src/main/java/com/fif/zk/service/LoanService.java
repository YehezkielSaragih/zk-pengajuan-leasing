package com.fif.zk.service;

import com.fif.zk.model.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getLoans();
    Loan getLoanById(int id);
    void addLoan(Loan loan);
    void updateLoan(Loan loan);
    void deleteLoan(int id);
    List<Loan> getLoansByCreditorId(int creditorId);
}
