package com.fif.zk.service.implementation;

import com.fif.zk.model.Loan;
import com.fif.zk.service.LoanService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {
    private static LoanServiceImpl instance = new LoanServiceImpl();
    private List<Loan> loans = new ArrayList<>();
    private int nextId = 1;

    private LoanServiceImpl() {}

    public static LoanServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Loan> getLoans() {
        return loans.stream()
                .filter(l -> l.getDeletedAt() == null)
                .collect(Collectors.toList());
    }

    @Override
    public void addLoan(Loan loan) {
        loan.setId(nextId++);
        loan.setCreatedAt(LocalDateTime.now());
        loans.add(loan);
    }

    @Override
    public void deleteLoan(int id) {
        loans.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .ifPresent(l -> l.setDeletedAt(LocalDateTime.now()));
    }

    @Override
    public Loan getLoanById(int id) {
        return loans.stream()
                .filter(l -> l.getId().equals(id) && l.getDeletedAt() == null)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateLoan(Loan updated) {
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getId().equals(updated.getId())) {
                updated.setUpdatedAt(LocalDateTime.now());
                loans.set(i, updated);
                break;
            }
        }
    }

    public List<Loan> getLoansByCreditorId(int creditorId) {
        return loans.stream()
                .filter(l -> l.getCreditorId() == creditorId)
                .collect(Collectors.toList());
    }
}