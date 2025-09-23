package com.fif.zk.dto;

public class LoanDashboardItem {
    private Integer loanId;
    private String creditorName;
    private Integer loanAmount;
    private String status;

    public LoanDashboardItem(Integer loanId, String creditorName, Integer loanAmount, String status) {
        this.loanId = loanId;
        this.creditorName = creditorName;
        this.loanAmount = loanAmount;
        this.status = status;
    }

    public Integer getLoanId() { return loanId; }
    public String getCreditorName() { return creditorName; }
    public Integer getLoanAmount() { return loanAmount; }
    public String getStatus() { return status; }
}
