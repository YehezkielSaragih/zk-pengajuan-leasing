package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import java.util.List;

public class CreditorFormViewModel {

    private String name;
    private Integer age;
    private String address;
    private Integer income;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Integer getIncome() { return income; }
    public void setIncome(Integer income) { this.income = income; }

    @Command
    public void save() {
        // Name not empty
        if (name == null || name.trim().isEmpty()) {
            Clients.showNotification("Name cannot be empty!", "error", null, "middle_center", 3000);
            return;
        }
        // Name minimum 3 characters
        if (name.trim().length() < 3) {
            Clients.showNotification("Name must be at least 3 characters!", "error", null, "middle_center", 3000);
            return;
        }
        // Duplicate name check
        List<Creditor> existingCreditors = CreditorServiceImpl.getInstance().getCreditors();
        boolean exists = existingCreditors.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name.trim()));
        if (exists) {
            Clients.showNotification("Creditor with this name already exists!", "error", null, "middle_center", 3000);
            return;
        }
        // Age validation
        if (age == null || age < 18) {
            Clients.showNotification("Creditor must be at least 18 years old!", "error", null, "middle_center", 3000);
            return;
        }
        // Address validation
        if (address == null || address.trim().isEmpty()) {
            Clients.showNotification("Address cannot be empty!", "error", null, "middle_center", 3000);
            return;
        }
        // Income validation
        if (income == null) {
            Clients.showNotification("Income cannot be empty!", "error", null, "middle_center", 3000);
            return;
        }
        Creditor creditor = new Creditor(name.trim(), age, address, income);
        CreditorServiceImpl.getInstance().addCreditor(creditor);
        Executions.sendRedirect("layout.zul?page=creditor-dashboard.zul");
    }
}
