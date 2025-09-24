package com.fif.zk.service.implementation;

import com.fif.zk.model.Loan;
import com.fif.zk.repository.LoanRepository;
import com.fif.zk.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> getLoans() {
        // filter yang tidak soft-deleted
        return loanRepository.findAll()
                .stream()
                .filter(l -> l.getDeletedAt() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Loan getLoanById(int id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void addLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public void updateLoan(Loan loan) {
        loanRepository.save(loan); // di JPA, save = insert or update
    }

    @Override
    public void deleteLoan(int id) {
        loanRepository.findById(id).ifPresent(l -> {
            l.setDeletedAt(java.time.LocalDateTime.now());
            loanRepository.save(l); // soft delete
        });
    }

    @Override
    public List<Loan> getLoansByCreditorId(int creditorId) {
        return loanRepository.findByCreditorIdAndDeletedAtIsNull(creditorId);
    }
}