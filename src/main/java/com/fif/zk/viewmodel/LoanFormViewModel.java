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

    private Creditor selectedCreditor;
    private String loanName;
    private String loanType;
    private Integer loanAmount;
    private Integer downPayment;
    private String status = "Pending"; // default

    private List<Creditor> creditors;

    // === Getters / Setters ===
    public Creditor getSelectedCreditor() { return selectedCreditor; }
    public void setSelectedCreditor(Creditor selectedCreditor) { this.selectedCreditor = selectedCreditor; }

    public String getLoanName() { return loanName; }
    public void setLoanName(String loanName) { this.loanName = loanName; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public Integer getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Integer loanAmount) { this.loanAmount = loanAmount; }

    public Integer getDownPayment() { return downPayment; }
    public void setDownPayment(Integer downPayment) { this.downPayment = downPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Creditor> getCreditors() { return creditors; }

    // === Initialize ===
    @Init
    public void init() {
        creditors = CreditorServiceImpl.getInstance().getCreditors()
                .stream()
                .filter(c -> c.getDeletedAt() == null)
                .collect(Collectors.toList());
    }

    // === Save Loan ===
    @Command
    public void save() {
        if (selectedCreditor == null) {
            Clients.showNotification("Please select a creditor!", "error", null, "middle_center", 3000);
            return;
        }
        if (loanName == null || loanName.isEmpty()) {
            Clients.showNotification("Please enter a loan name!", "error", null, "middle_center", 3000);
            return;
        }
        if (loanType == null || loanType.isEmpty()) {
            Clients.showNotification("Please enter a loan type!", "error", null, "middle_center", 3000);
            return;
        }
        if (loanAmount == null || loanAmount <= 0) {
            Clients.showNotification("Please enter a valid loan amount!", "error", null, "middle_center", 3000);
            return;
        }

        Loan loan = new Loan(
                selectedCreditor.getId(),
                loanName,
                loanType,
                loanAmount,
                downPayment != null ? downPayment : 0,
                status
        );

        LoanServiceImpl.getInstance().addLoan(loan);
        selectedCreditor.addLoan(loan);
        CreditorServiceImpl.getInstance().updateCreditor(selectedCreditor);

        Clients.showNotification("Loan saved successfully!", "info", null, "top_center", 2000);
        Executions.sendRedirect("layout.zul?page=loanDashboard.zul");
    }
}
