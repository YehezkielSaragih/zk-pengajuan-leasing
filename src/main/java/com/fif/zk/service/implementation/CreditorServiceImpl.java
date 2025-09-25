package com.fif.zk.service.implementation;

import com.fif.zk.model.Creditor;
import com.fif.zk.repository.CreditorRepository;
import com.fif.zk.service.CreditorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreditorServiceImpl implements CreditorService {

    @Autowired
    private CreditorRepository repository;

    @Override
    public List<Creditor> getCreditors() {
        return repository.findByDeletedAtIsNull();
    }

    @Override
    public Creditor getCreditorById(int id) {
        return repository.findByIdAndDeletedAtIsNull(id);
    }

    @Override
    public void addCreditor(Creditor creditor) {
        creditor.setCreatedAt(LocalDateTime.now());
        creditor.setUpdatedAt(LocalDateTime.now());
        repository.save(creditor);
    }

    @Override
    public void updateCreditor(Creditor creditor) {
        creditor.setUpdatedAt(LocalDateTime.now());
        repository.save(creditor);
    }

    @Override
    public void deleteCreditor(int id) {
        repository.findById(id).ifPresent(c -> {
            c.setUpdatedAt(LocalDateTime.now());
            c.setDeletedAt(LocalDateTime.now());
            repository.save(c);
        });
    }
}