package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.util.List;

public class DashboardViewModel {

    public List<Creditor> getCreditors() {
        return CreditorService.getInstance().getCreditors();
    }

    @Command
    public void editCreditor(@ContextParam(ContextType.TRIGGER_EVENT) org.zkoss.zk.ui.event.Event event,
                             @org.zkoss.bind.annotation.BindingParam("id") int id) {
        // Redirect ke halaman detail.zul sambil membawa parameter id
        Executions.sendRedirect("/pages/layout.zul?page=/pages/detail.zul&id=" + id);
    }

    @Command
    @NotifyChange("creditors")
    public void deleteCreditor(@org.zkoss.bind.annotation.BindingParam("id") int id) {
        CreditorService.getInstance().deleteCreditor(id);
    }
}
