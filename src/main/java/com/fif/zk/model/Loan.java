package com.fif.zk.model;

import java.time.LocalDateTime;

public class Loan {

    private Integer id;
    private Integer creditorId;   // FK ke Creditor

    private String loanName;      // nama pinjaman
    private String loanType;      // tipe pinjaman
    private Integer loanAmount;   // total pinjaman
    private Integer downPayment;
    private String status;        // PENDING, APPROVED, REJECTED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Loan() {}

    public Loan(Integer creditorId, String loanName, String loanType, Integer loanAmount, Integer downPayment, String status) {
        this.creditorId = creditorId;
        this.loanName = loanName;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.downPayment = downPayment;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCreditorId() { return creditorId; }
    public void setCreditorId(Integer creditorId) { this.creditorId = creditorId; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
