package com.fif.zk.service.implementation;

import com.fif.zk.model.Creditor;
import com.fif.zk.repository.CreditorRepository;
import com.fif.zk.service.CreditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditorServiceImpl implements CreditorService {

    @Autowired
    private CreditorRepository repository;

    @Override
    public List<Creditor> getCreditors() {
        // filter kalau ada soft delete (misalnya pakai field deletedAt di Creditor)
        return repository.findAll()
                .stream()
                .filter(c -> c.getDeletedAt() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Creditor getCreditorById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void addCreditor(Creditor creditor) {
        repository.save(creditor);
    }

    @Override
    public void updateCreditor(Creditor creditor) {
        repository.save(creditor); // save = insert/update
    }

    @Override
    public void deleteCreditor(int id) {
        repository.findById(id).ifPresent(c -> {
            c.setDeletedAt(LocalDateTime.now()); // soft delete
            repository.save(c);
        });
    }
}