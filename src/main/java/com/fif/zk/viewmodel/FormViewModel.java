package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class FormViewModel {

    private String name;
    private int age;
    private double income;
    private double downPayment;
    private double loanAmount;

    // Getters & Setters
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

    @Command
    public void save() {
        CreditorService.getInstance().addCreditor(
                new Creditor(name, age, income, downPayment, loanAmount)
        );
        Executions.sendRedirect("layout.zul?page=dashboard.zul");
    }
}
