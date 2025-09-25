package com.fif.zk.service.implementation;

import com.fif.zk.model.LoanType;
import com.fif.zk.repository.LoanTypeRepository;
import java.util.List;
import java.util.Optional;

import com.fif.zk.service.LoanTypeService;
import org.springframework.stereotype.Service;

@Service
public class LoanTypeServiceImpl implements LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;

    public LoanTypeServiceImpl(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    @Override
    public List<LoanType> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    @Override
    public Optional<LoanType> getLoanTypeById(Integer id) {
        return loanTypeRepository.findById(id);
    }

    @Override
    public LoanType createLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    @Override
    public LoanType updateLoanType(LoanType loanType) {
        return loanTypeRepository.save(loanType);
    }

    @Override
    public void deleteLoanType(Integer id) {
        loanTypeRepository.deleteById(id);
    }
}
