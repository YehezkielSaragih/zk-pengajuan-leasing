package com.fif.zk.model;

import java.time.LocalDateTime;

public class Loan {

    private Integer id;
    private Integer creditorId;   // FK ke Creditor
    private Integer amount;
    private Integer downPayment;
    private String status;        // PENDING, APPROVED, REJECTED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Loan() {}

    public Loan(Integer creditorId, Integer amount, Integer downPayment, String status) {
        this.creditorId = creditorId;
        this.amount = amount;
        this.downPayment = downPayment;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCreditorId() { return creditorId; }
    public void setCreditorId(Integer creditorId) { this.creditorId = creditorId; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

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
