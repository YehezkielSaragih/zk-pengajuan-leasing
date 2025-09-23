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

    // Getters & Setters
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
        // 1. Check for empty name
        if (name == null || name.trim().isEmpty()) {
            Clients.showNotification("Name cannot be empty!", "error", null, "middle_center", 3000);
            return;
        }
        // 2. Check if creditor with the same name already exists
        List<Creditor> existingCreditors = CreditorServiceImpl.getInstance().getCreditors();
        boolean exists = existingCreditors.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(name.trim()));
        if (exists) {
            Clients.showNotification("Creditor with this name already exists!", "error", null, "middle_center", 3000);
            return;
        }
        // 3. Save new creditor
        Creditor creditor = new Creditor(name.trim(), age, address, income);
        CreditorServiceImpl.getInstance().addCreditor(creditor);
        // 4. Redirect back to dashboard
        Executions.sendRedirect("layout.zul?page=creditorDashboard.zul");
    }
}
