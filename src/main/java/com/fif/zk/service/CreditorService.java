package com.fif.zk.service;

import com.fif.zk.model.Creditor;

import java.util.List;

public interface CreditorService {

    List<Creditor> getCreditors();
    Creditor getCreditorById(int id);
    void addCreditor(Creditor creditor);
    void updateCreditor(Creditor creditor);
    void deleteCreditor(int id);
}
