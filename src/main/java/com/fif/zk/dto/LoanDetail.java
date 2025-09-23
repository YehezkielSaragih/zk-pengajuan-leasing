package com.fif.zk.dto;

public class LoanDetail {
    private int id;
    private String creditorName;
    private int loanAmount;
    private int downPayment; // add this
    private String status;

    // === Getters & Setters ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCreditorName() { return creditorName; }
    public void setCreditorName(String creditorName) { this.creditorName = creditorName; }

    public int getLoanAmount() { return loanAmount; }
    public void setLoanAmount(int loanAmount) { this.loanAmount = loanAmount; }

    public int getDownPayment() { return downPayment; }
    public void setDownPayment(int downPayment) { this.downPayment = downPayment; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
