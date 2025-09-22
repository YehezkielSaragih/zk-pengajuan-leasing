package com.fif.zk.model;

public class Creditor {

    private Integer id;
    private String name;
    private Integer age;
    private Integer income;
    private Integer downPayment;
    private Integer loanAmount;
    private String status;

    public Creditor() {}

    public Creditor(String name, Integer age, Integer income,
                    Integer downPayment, Integer loanAmount, String status) {
        this.name = name;
        this.age = age;
        this.income = income;
        this.downPayment = downPayment;
        this.loanAmount = loanAmount;
        this.status = status;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Integer getIncome() { return income; }
    public void setIncome(Integer income) { this.income = income; }

    public Integer getDownPayment() { return downPayment; }
    public void setDownPayment(Integer downPayment) { this.downPayment = downPayment; }

    public Integer getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Integer loanAmount) { this.loanAmount = loanAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
