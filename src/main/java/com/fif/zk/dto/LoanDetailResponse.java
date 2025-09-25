package com.fif.zk.dto;

public class LoanDetailResponse {
    private int id;
    private String creditorName;
    private String loanName;
    private String loanType;
    private int loanAmount;
    private int downPayment;
    private String status;

    // === Getters & Setters ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCreditorName() { return creditorName; }
    public void setCreditorName(String creditorName) { this.creditorName = creditorName; }

    public String getLoanName() { return loanName; }
    public void setLoanName(String loanName) { this.loanName = loanName; }

    public String getLoanType() { return loanType; }
    public void setLoanType(String loanType) { this.loanType = loanType; }

    public int getLoanAmount() { return loanAmount; }
    public void setLoanAmount(int loanAmount) { this.loanAmount = loanAmount; }

    public int getDownPayment() { return downPayment; }
    public void setDownPayment(int downPayment) { this.downPayment = downPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = formatStatus(status);
    }

    // --- Helper untuk format status ---
    private String formatStatus(String status) {
        if (status == null || status.isEmpty()) return status;
        return status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
    }
}
