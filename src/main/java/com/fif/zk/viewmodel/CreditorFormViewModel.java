package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class CreditorFormViewModel {

    // --- Services ---
    @WireVariable("creditorServiceImpl")
    private CreditorService creditorService;

    // --- Fields ---
    private String name;
    private Integer age;
    private String address;
    private Integer income;

    // --- Getters & Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getIncome() { return income; }
    public void setIncome(Integer income) { this.income = income; }

    // --- Commands ---
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
        List<Creditor> existingCreditors = creditorService.getCreditors();
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

        // Save creditor
        Creditor creditor = new Creditor(name.trim(), age, address, income);
        creditorService.addCreditor(creditor);

        // Redirect
        Executions.sendRedirect("layout.zul?page=creditor-dashboard.zul");
    }
}
