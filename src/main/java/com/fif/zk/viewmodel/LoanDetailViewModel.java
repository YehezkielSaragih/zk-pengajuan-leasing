package com.fif.zk.viewmodel;

import com.fif.zk.dto.LoanDetail;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class LoanDetailViewModel {
    private LoanDetail loan;

    public LoanDetail getLoan() { return loan; }

    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int loanId = Integer.parseInt(idParam);
            Loan l = LoanServiceImpl.getInstance().getLoanById(loanId);
            if (l != null) {
                Creditor c = CreditorServiceImpl.getInstance().getCreditorById(l.getCreditorId());
                loan = new LoanDetail();
                loan.setId(l.getId());
                loan.setLoanAmount(l.getAmount());
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
                l.setStatus(loan.getStatus());
                LoanServiceImpl.getInstance().updateLoan(l);
            }
        }
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loanDashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loanDashboard.zul");
    }
}
