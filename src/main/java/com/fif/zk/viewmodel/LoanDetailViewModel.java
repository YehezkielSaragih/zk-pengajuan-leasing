package com.fif.zk.viewmodel;

import com.fif.zk.dto.LoanDetailResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class LoanDetailViewModel {

    private LoanDetailResponse loan;

    public LoanDetailResponse getLoan() { return loan; }

    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int loanId = Integer.parseInt(idParam);
            Loan l = LoanServiceImpl.getInstance().getLoanById(loanId);
            if (l != null) {
                Creditor c = CreditorServiceImpl.getInstance().getCreditorById(l.getCreditor().getId());
                loan = new LoanDetailResponse();
                loan.setId(l.getId());
                loan.setLoanName(l.getLoanName());
                loan.setLoanType(l.getLoanType());
                loan.setLoanAmount(l.getLoanAmount());
                loan.setDownPayment(l.getDownPayment());
                loan.setStatus(l.getStatus());
                loan.setCreditorName(c != null ? c.getName() : "Unknown");
            }
        }
    }

    @Command
    public void update() {
        if (loan != null) {
            Loan l = LoanServiceImpl.getInstance().getLoanById(loan.getId());
            if (l != null) {
                // Update status saja
                l.setStatus(loan.getStatus());
                LoanServiceImpl.getInstance().updateLoan(l);
            }
        }
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loan-dashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loan-dashboard.zul");
    }
}
