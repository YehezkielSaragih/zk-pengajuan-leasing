package com.fif.zk.service;

import com.fif.zk.model.Creditor;
import java.util.ArrayList;
import java.util.List;

public class CreditorService {
    private static CreditorService instance = new CreditorService();
    private List<Creditor> creditors = new ArrayList<>();
    private int nextId = 1; // untuk auto-increment

    private CreditorService() {}

    public static CreditorService getInstance() {
        return instance;
    }

    public List<Creditor> getCreditors() {
        return creditors;
    }

    public void addCreditor(Creditor creditor) {
        creditor.setId(nextId++);  // set auto-increment ID
        creditors.add(creditor);
    }

    public void deleteCreditor(int id) {
        creditors.removeIf(c -> c.getId() == id);
    }

    public Creditor findById(int id) {
        return creditors.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void updateCreditor(Creditor updatedCreditor) {
        for (int i = 0; i < creditors.size(); i++) {
            if (creditors.get(i).getId().equals(updatedCreditor.getId())) {
                creditors.set(i, updatedCreditor);
                break;
            }
        }
    }
}
