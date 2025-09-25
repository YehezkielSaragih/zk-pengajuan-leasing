package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.model.LoanType;
import com.fif.zk.service.CreditorService;
import com.fif.zk.service.LoanService;
import com.fif.zk.service.LoanTypeService;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.List;
import java.util.stream.Collectors;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class LoanFormViewModel {

    // --- Data fields ---
    private Creditor selectedCreditor;
    private String loanName;
    private LoanType selectedLoanType;
    private Integer loanAmount;
    private Integer downPayment;
    private String status = "Pending"; // default

    private List<Creditor> creditors;
    private List<LoanType> loanTypes;

    // --- Services ---
    @WireVariable("creditorServiceImpl")
    private CreditorService creditorService;

    @WireVariable("loanServiceImpl")
    private LoanService loanService;

    @WireVariable("loanTypeServiceImpl")
    private LoanTypeService loanTypeService;

    // --- Getters & Setters ---
    public Creditor getSelectedCreditor() { return selectedCreditor; }
    public void setSelectedCreditor(Creditor selectedCreditor) { this.selectedCreditor = selectedCreditor; }

    public String getLoanName() { return loanName; }
    public void setLoanName(String loanName) { this.loanName = loanName; }

    public LoanType getSelectedLoanType() { return selectedLoanType; }
    public void setSelectedLoanType(LoanType selectedLoanType) { this.selectedLoanType = selectedLoanType; }

    public Integer getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Integer loanAmount) { this.loanAmount = loanAmount; }

    public Integer getDownPayment() { return downPayment; }
    public void setDownPayment(Integer downPayment) { this.downPayment = downPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Creditor> getCreditors() { return creditors; }
    public List<LoanType> getLoanTypes() { return loanTypes; }

    // --- Init ---
    @Init
    public void init() {
        creditors = creditorService.getCreditors()
                .stream()
                .filter(c -> c.getDeletedAt() == null)
                .collect(Collectors.toList());

        loanTypes = loanTypeService.getAllLoanTypes(); // fetch all loan types
    }

    // --- Commands ---
    @Command
    public void save() {
        if (selectedCreditor == null) {
            Clients.showNotification("Please select a creditor!", "error", null, "top_right", 3000);
            return;
        }
        if (loanName == null || loanName.isEmpty()) {
            Clients.showNotification("Please enter a loan name!", "error", null, "top_right", 3000);
            return;
        }
        if (selectedLoanType == null) {
            Clients.showNotification("Please select a loan type!", "error", null, "top_right", 3000);
            return;
        }
        if (downPayment == null) {
            Clients.showNotification("Down payment must be filled!", "error", null, "top_right", 3000);
            return;
        }
        if (loanAmount == null || loanAmount <= 0) {
            Clients.showNotification("Please enter a valid loan amount!", "error", null, "top_right", 3000);
            return;
        }

        double minDP = loanAmount * 0.3;
        if (downPayment < minDP) {
            Clients.showNotification(
                    "Down payment must be at least 30% of total loan (" + (int) minDP + ")!",
                    "error", null, "top_right", 4000
            );
            return;
        }
        if (downPayment >= loanAmount) {
            Clients.showNotification(
                    "Down payment must be less than total loan!",
                    "error", null, "top_right", 4000
            );
            return;
        }

        Loan loan = new Loan(
                selectedCreditor,
                loanName,
                selectedLoanType,
                loanAmount,
                downPayment,
                status
        );

        loanService.addLoan(loan);
        creditorService.updateCreditor(selectedCreditor);

        Executions.sendRedirect("layout.zul?page=loan-dashboard.zul&success=true");
    }

    @Command
    public void back() {
        Executions.sendRedirect("layout.zul?page=loan-dashboard.zul");
    }
}