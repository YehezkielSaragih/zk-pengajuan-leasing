package com.fif.zk.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Creditor {

    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Integer income;

    private List<Loan> loans = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Creditor() {}

    public Creditor(String name, Integer age, String address, Integer income) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.income = income;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getIncome() { return income; }
    public void setIncome(Integer income) { this.income = income; }

    public List<Loan> getLoans() { return loans; }
    public void setLoans(List<Loan> loans) { this.loans = loans; }
    public void addLoan(Loan loan) { this.loans.add(loan); }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
