package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class DetailViewModel {

    private Creditor creditor;

    public Creditor getCreditor() {
        return creditor;
    }

    @Init
    public void init() {
        // Ambil parameter id dari URL
        String idParam = Executions.getCurrent().getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            creditor = CreditorService.getInstance().getCreditorById(id);
        }
    }

    @Command
    public void update() {
        if (creditor != null) {
            CreditorService.getInstance().updateCreditor(creditor);
        }
        Executions.sendRedirect("layout.zul?page=dashboard.zul");
    }

    @Command
    public void delete() {
        if (creditor != null) {
            CreditorService.getInstance().deleteCreditor(creditor.getId());
        }
        Executions.sendRedirect("layout.zul?page=dashboard.zul");
    }

    @Command
    public void back() {
        Executions.sendRedirect("layout.zul?page=dashboard.zul");
    }
}
