package com.fif.zk.dto;

import com.fif.zk.model.Creditor;

public class CreditorDetailResponse {
    private Creditor creditor;
    private int totalLoans;
    private int pendingLoans;
    private int approvedLoans;
    private int rejectedLoans;

    // Constructor default harus pakai nama class yang sama
    public CreditorDetailResponse() {}

    public CreditorDetailResponse(Creditor creditor, int totalLoans, int pendingLoans, int approvedLoans, int rejectedLoans) {
        this.creditor = creditor;
        this.totalLoans = totalLoans;
        this.pendingLoans = pendingLoans;
        this.approvedLoans = approvedLoans;
        this.rejectedLoans = rejectedLoans;
    }

    // Getters & Setters
    public Creditor getCreditor() { return creditor; }
    public void setCreditor(Creditor creditor) { this.creditor = creditor; }
    public int getTotalLoans() { return totalLoans; }
    public void setTotalLoans(int totalLoans) { this.totalLoans = totalLoans; }
    public int getPendingLoans() { return pendingLoans; }
    public void setPendingLoans(int pendingLoans) { this.pendingLoans = pendingLoans; }
    public int getApprovedLoans() { return approvedLoans; }
    public void setApprovedLoans(int approvedLoans) { this.approvedLoans = approvedLoans; }
    public int getRejectedLoans() { return rejectedLoans; }
    public void setRejectedLoans(int rejectedLoans) { this.rejectedLoans = rejectedLoans; }
}
