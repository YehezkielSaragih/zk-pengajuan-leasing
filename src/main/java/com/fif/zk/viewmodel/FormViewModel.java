package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class FormViewModel {

    private String name;
    private Integer age;
    private Integer income;
    private Integer downPayment;
    private Integer loanAmount;
    private String status;

    // Getters & Setters
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

    @Command
    public void save() {
        CreditorServiceImpl.getInstance().addCreditor(
                new Creditor(name, age, income, downPayment, loanAmount, "Pending")
        );
        Executions.sendRedirect("layout.zul?page=dashboard.zul");
    }
}
