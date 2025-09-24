package com.fif.zk.dto;

import java.text.NumberFormat;
import java.util.Locale;

public class LoanDashboardResponse {
    private Integer loanId;
    private String creditorName;
    private String loanName;
    private String loanType;
    private Integer loanAmount;
    private Integer downPayment;
    private String status;

    public LoanDashboardResponse(Integer loanId, String creditorName,
                                 String loanName, String loanType,
                                 Integer loanAmount, Integer downPayment,
                                 String status) {
        this.loanId = loanId;
        this.creditorName = creditorName != null ? creditorName : "Unknown";
        this.loanName = loanName != null ? loanName : "";
        this.loanType = loanType != null ? loanType : "";
        this.loanAmount = loanAmount != null ? loanAmount : 0;
        this.downPayment = downPayment != null ? downPayment : 0;
        this.status = status != null ? status : "Pending";
    }

    // ===== Getters =====
    public Integer getLoanId() { return loanId; }
    public String getCreditorName() { return creditorName; }
    public String getLoanName() { return loanName; }
    public String getLoanType() { return loanType; }
    public Integer getLoanAmount() { return loanAmount; }
    public Integer getDownPayment() { return downPayment; }
    public String getStatus() { return status; }

    // ===== Setters =====
    public void setLoanId(Integer loanId) { this.loanId = loanId; }
    public void setCreditorName(String creditorName) { this.creditorName = creditorName; }
    public void setLoanName(String loanName) { this.loanName = loanName; }
    public void setLoanType(String loanType) { this.loanType = loanType; }
    public void setLoanAmount(Integer loanAmount) { this.loanAmount = loanAmount; }
    public void setDownPayment(Integer downPayment) { this.downPayment = downPayment; }
    public void setStatus(String status) { this.status = status; }

    public String getLoanAmountFormatted() {
        return formatRupiah(loanAmount);
    }

    public String getDownPaymentFormatted() {
        return formatRupiah(downPayment);
    }

    private String formatRupiah(Integer value) {
        if (value == null) return "-";
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return nf.format(value);
    }
}
