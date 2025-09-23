package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import java.util.List;
import java.util.stream.Collectors;

public class CreditorDetailViewModel {
    private Creditor creditor;
    private int totalLoans;
    private int pendingLoans;
    private int approvedLoans;
    private int rejectedLoans;

    public Creditor getCreditor() { return creditor; }
    public int getTotalLoans() { return totalLoans; }
    public int getPendingLoans() { return pendingLoans; }
    public int getApprovedLoans() { return approvedLoans; }
    public int getRejectedLoans() { return rejectedLoans; }

    @Init
    public void init() {
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int creditorId = Integer.parseInt(idParam);
            creditor = CreditorServiceImpl.getInstance().getCreditorById(creditorId);

            if (creditor != null) {
                List<Loan> loans = LoanServiceImpl.getInstance().getLoansByCreditorId(creditorId)
                        .stream()
                        .filter(l -> l.getDeletedAt() == null)  // exclude soft-deleted loans
                        .collect(Collectors.toList());

                totalLoans = loans.size();
                pendingLoans = (int) loans.stream().filter(l -> "Pending".equalsIgnoreCase(l.getStatus())).count();
                approvedLoans = (int) loans.stream().filter(l -> "Approved".equalsIgnoreCase(l.getStatus())).count();
                rejectedLoans = (int) loans.stream().filter(l -> "Rejected".equalsIgnoreCase(l.getStatus())).count();
            }
        }
    }

    @Command
    public void save() {
        if (creditor != null) {
            CreditorServiceImpl.getInstance().updateCreditor(creditor);
        }
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditorDashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditorDashboard.zul");
    }
}
