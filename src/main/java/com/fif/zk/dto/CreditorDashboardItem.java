package com.fif.zk.dto;

public class CreditorDashboardItem {
    private Integer id;
    private String name;
    private Integer income;
    private Long activeLoanCount;
    private Long rejectedLoanCount;
    private Long pendingLoanCount;

    // constructor
    public CreditorDashboardItem(Integer id, String name, Integer income,
                                Long activeLoanCount, Long rejectedLoanCount, Long pendingLoanCount) {
        this.id = id;
        this.name = name;
        this.income = income;
        this.activeLoanCount = activeLoanCount;
        this.rejectedLoanCount = rejectedLoanCount;
        this.pendingLoanCount = pendingLoanCount;
    }

    // getters & setters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public Integer getIncome() { return income; }
    public Long getActiveLoanCount() { return activeLoanCount; }
    public Long getRejectedLoanCount() { return rejectedLoanCount; }
    public Long getPendingLoanCount() { return pendingLoanCount; }

    public String getTotalLoanString() {
        return activeLoanCount + " Active, " + rejectedLoanCount + " Rejected, " + pendingLoanCount + " Pending";
    }
}
