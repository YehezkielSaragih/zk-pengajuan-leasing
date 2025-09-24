package com.fif.zk.service.implementation;

import com.fif.zk.model.Creditor;
import com.fif.zk.repository.CreditorRepository;
import com.fif.zk.service.CreditorService;

import java.util.List;

public class CreditorServiceImpl implements CreditorService {

    private static CreditorServiceImpl instance = new CreditorServiceImpl();
    private CreditorRepository repository = CreditorRepository.getInstance();

    private CreditorServiceImpl() {}

    public static CreditorServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Creditor> getCreditors() {
        return repository.findAll();
    }

    @Override
    public Creditor getCreditorById(int id) {
        return repository.findById(id);
    }

    @Override
    public void addCreditor(Creditor creditor) {
        repository.save(creditor);
    }

    @Override
    public void updateCreditor(Creditor creditor) {
        repository.update(creditor);
    }

    @Override
    public void deleteCreditor(int id) {
        repository.softDelete(id);
    }
}
