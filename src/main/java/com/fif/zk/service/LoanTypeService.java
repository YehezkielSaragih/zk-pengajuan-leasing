package com.fif.zk.service;

import com.fif.zk.model.LoanType;
import java.util.List;
import java.util.Optional;

public interface LoanTypeService {

    List<LoanType> getAllLoanTypes();
    Optional<LoanType> getLoanTypeById(Integer id);
    LoanType createLoanType(LoanType loanType);
    LoanType updateLoanType(LoanType loanType);
    void deleteLoanType(Integer id);
}
