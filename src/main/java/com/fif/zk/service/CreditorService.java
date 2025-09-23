package com.fif.zk.service;

import com.fif.zk.model.Creditor;

import java.util.List;

public interface CreditorService {
    List<Creditor> getCreditors();

    void addCreditor(Creditor creditor);

    void deleteCreditor(int id);

    Creditor getCreditorById(int id);

    void updateCreditor(Creditor updated);
}
