package com.fif.zk.service.implementation;

import com.fif.zk.model.Loan;
import com.fif.zk.repository.LoanRepository;
import com.fif.zk.service.LoanService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {

    private static LoanServiceImpl instance = new LoanServiceImpl();
    private LoanRepository loanRepository = LoanRepository.getInstance();

    private LoanServiceImpl() {}

    public static LoanServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan getLoanById(int id) {
        return loanRepository.findById(id);
    }

    @Override
    public void addLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public void updateLoan(Loan loan) {
        loanRepository.update(loan);
    }

    @Override
    public void deleteLoan(int id) {
        loanRepository.softDelete(id);
    }

    @Override
    public List<Loan> getLoansByCreditorId(int creditorId) {
        return loanRepository.findByCreditorId(creditorId);
    }
}
