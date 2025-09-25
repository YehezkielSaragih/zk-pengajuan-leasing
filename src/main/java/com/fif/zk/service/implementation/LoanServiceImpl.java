package com.fif.zk.service.implementation;

import com.fif.zk.model.Loan;
import com.fif.zk.repository.LoanRepository;
import com.fif.zk.service.LoanService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Transactional(readOnly = true)
    public List<Loan> getLoans() {
        List<Loan> loans = loanRepository.findByDeletedAtIsNull();
        loans.forEach(l -> {
            Hibernate.initialize(l.getLoanType());
            Hibernate.initialize(l.getCreditor());
        });

        return loans;
    }

    @Override
    @Transactional(readOnly = true)
    public Loan getLoanById(int id) {
        Loan loan = loanRepository.findById(id)
                .filter(l -> l.getDeletedAt() == null)
                .orElse(null);
        if (loan != null) {
            Hibernate.initialize(loan.getLoanType());
            Hibernate.initialize(loan.getCreditor());
        }
        return loan;
    }

    @Override
    public void addLoan(Loan loan) {
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());
        loanRepository.save(loan);
    }

    @Override
    public void updateLoan(Loan loan) {
        loan.setUpdatedAt(LocalDateTime.now());
        loanRepository.save(loan);
    }

    @Override
    public void deleteLoan(int id) {
        loanRepository.findById(id).ifPresent(l -> {
            l.setUpdatedAt(LocalDateTime.now());
            l.setDeletedAt(LocalDateTime.now());
            loanRepository.save(l);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByCreditorId(int creditorId) {
        List<Loan> loans = loanRepository.findByCreditorIdAndDeletedAtIsNull(creditorId);
        loans.forEach(l -> {
            Hibernate.initialize(l.getLoanType());
            Hibernate.initialize(l.getCreditor());
        });

        return loans;
    }

}
