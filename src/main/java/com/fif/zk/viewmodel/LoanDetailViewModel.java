package com.fif.zk.viewmodel;

import com.fif.zk.dto.LoanDetailResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.model.LoanStatus;
import com.fif.zk.service.CreditorService;
import com.fif.zk.service.LoanService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class LoanDetailViewModel {

    // --- Data fields ---
    private LoanDetailResponse loan;

    // --- Services ---
    @WireVariable("creditorServiceImpl")
    private CreditorService creditorService;

    @WireVariable("loanServiceImpl")
    private LoanService loanService;

    // --- Getter ---
    public LoanDetailResponse getLoan() {
        return loan;
    }

    // --- Init ---
    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int loanId = Integer.parseInt(idParam);
            Loan l = loanService.getLoanById(loanId);

            if (l != null) {
                // Force load loanType sebelum session close
                Hibernate.initialize(l.getLoanType());

                Creditor c = creditorService.getCreditorById(l.getCreditor().getId());

                loan = new LoanDetailResponse();
                loan.setId(l.getId());
                loan.setLoanName(l.getLoanName());
                loan.setLoanType(l.getLoanType().getName());
                loan.setLoanAmount(l.getLoanAmount());
                loan.setDownPayment(l.getDownPayment());
                loan.setStatus(l.getStatus().name());
                loan.setCreditorName(c != null ? c.getName() : "Unknown");
            }
        }
    }


    // --- Commands ---
    @Command
    public void update() {
        if (loan != null) {
            Loan l = loanService.getLoanById(loan.getId());
            if (l != null) {
                LoanStatus newStatus = LoanStatus.valueOf(loan.getStatus().toUpperCase());
                l.setStatus(newStatus);
                loanService.updateLoan(l);
            }
        }
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loan-dashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loan-dashboard.zul");
    }
}