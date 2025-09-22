package com.fif.zk.model;

public class Creditor {

    private String name;
    private int age;
    private double income;
    private double downPayment;
    private double loanAmount;

    public Creditor() {}
    public Creditor(String name, int age, double income, double downPayment, double loanAmount) {
        this.name = name; this.age = age; this.income = income;
        this.downPayment = downPayment; this.loanAmount = loanAmount;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getIncome() { return income; }
    public void setIncome(double income) { this.income = income; }

    public double getDownPayment() { return downPayment; }
    public void setDownPayment(double downPayment) { this.downPayment = downPayment; }

    public double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(double loanAmount) { this.loanAmount = loanAmount; }
}
