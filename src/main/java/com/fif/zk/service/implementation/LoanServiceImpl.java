package com.fif.zk.service.implementation;

import com.fif.zk.model.Loan;
import com.fif.zk.repository.LoanRepository;
import com.fif.zk.service.LoanService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Transactional(readOnly = true)
    public List<Loan> getLoans() {
        List<Loan> loans = loanRepository.findAll();

        // Paksa load relasi yang dibutuhkan
        loans.forEach(l -> {
            Hibernate.initialize(l.getLoanType());
            Hibernate.initialize(l.getCreditor());
        });

        return loans;
    }

    @Override
    @Transactional(readOnly = true)
    public Loan getLoanById(int id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan != null) {
            Hibernate.initialize(loan.getLoanType());
            Hibernate.initialize(loan.getCreditor());
        }
        return loan;
    }


    @Override
    public void addLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public void updateLoan(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public void deleteLoan(int id) {
        loanRepository.findById(id).ifPresent(l -> {
            l.setDeletedAt(java.time.LocalDateTime.now());
            loanRepository.save(l); // soft delete
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByCreditorId(int creditorId) {
        List<Loan> loans = loanRepository.findByCreditorIdAndDeletedAtIsNull(creditorId);

        // paksa initialize masih di dalam transactional context
        loans.forEach(l -> {
            Hibernate.initialize(l.getLoanType());
            Hibernate.initialize(l.getCreditor());
        });

        return loans;
    }

}
