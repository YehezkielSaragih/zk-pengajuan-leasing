package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import java.util.List;
import java.util.stream.Collectors;

public class LoanFormViewModel {

    private String selectedCreditorName;
    private Integer loanAmount;
    private Integer downPayment;
    private String status = "Pending"; // default
    private List<String> creditorNames;

    // === Getters / Setters ===
    public String getSelectedCreditorName() { return selectedCreditorName; }
    public void setSelectedCreditorName(String selectedCreditorName) { this.selectedCreditorName = selectedCreditorName; }

    public Integer getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Integer loanAmount) { this.loanAmount = loanAmount; }

    public Integer getDownPayment() { return downPayment; }
    public void setDownPayment(Integer downPayment) { this.downPayment = downPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getCreditorNames() { return creditorNames; }

    // === Initialize creditor list ===
    @Init
    public void init() {
        creditorNames = CreditorServiceImpl.getInstance().getCreditors()
                .stream()
                .filter(c -> c.getDeletedAt() == null) // exclude soft-deleted
                .map(Creditor::getName)
                .collect(Collectors.toList());
    }

    // === Save Loan ===
    @Command
    public void save() {
        if (selectedCreditorName == null || selectedCreditorName.isEmpty()) {
            Clients.showNotification("Please select a creditor!", "error", null, "middle_center", 3000);
            return;
        }

        // Find the Creditor object by name
        Creditor creditor = CreditorServiceImpl.getInstance().getCreditors()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(selectedCreditorName))
                .findFirst()
                .orElse(null);

        if (creditor == null) {
            Clients.showNotification("Creditor not found!", "error", null, "middle_center", 3000);
            return;
        }

        Loan loan = new Loan(creditor.getId(), loanAmount, downPayment, status);
        LoanServiceImpl.getInstance().addLoan(loan);

        Executions.sendRedirect("layout.zul?page=loanDashboard.zul");
    }
}
