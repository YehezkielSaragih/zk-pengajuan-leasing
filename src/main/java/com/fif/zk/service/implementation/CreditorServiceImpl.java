package com.fif.zk.service.implementation;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;

import java.util.ArrayList;
import java.util.List;

public class CreditorServiceImpl implements CreditorService {
    private static CreditorServiceImpl instance = new CreditorServiceImpl();
    private List<Creditor> creditors = new ArrayList<>();
    private int nextId = 1; // untuk auto-increment

    private CreditorServiceImpl() {}

    public static CreditorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Creditor> getCreditors() {
        return creditors;
    }

    @Override
    public void addCreditor(Creditor creditor) {
        creditor.setId(nextId++);
        creditors.add(creditor);
    }

    @Override
    public void deleteCreditor(int id) {
        creditors.removeIf(c -> c.getId() == id);
    }

    @Override
    public Creditor getCreditorById(int id) {
        return creditors.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateCreditor(Creditor updated) {
        for (int i = 0; i < creditors.size(); i++) {
            if (creditors.get(i).getId().equals(updated.getId())) {
                creditors.set(i, updated);
                break;
            }
        }
    }
}
