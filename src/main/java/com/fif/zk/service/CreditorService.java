package com.fif.zk.service;

import com.fif.zk.model.Creditor;

import java.util.ArrayList;
import java.util.List;

public class CreditorService {
    private static CreditorService instance = new CreditorService();
    private List<Creditor> creditors = new ArrayList<>();

    private CreditorService() {}

    public static CreditorService getInstance() {
        return instance;
    }

    public List<Creditor> getCreditors() {
        return creditors;
    }

    public void addCreditor(Creditor creditor) {
        creditors.add(creditor);
    }
}
