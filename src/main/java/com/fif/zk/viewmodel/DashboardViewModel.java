package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import java.util.List;

public class DashboardViewModel {
    public List<Creditor> getCreditors() {
        return CreditorService.getInstance().getCreditors();
    }
}
