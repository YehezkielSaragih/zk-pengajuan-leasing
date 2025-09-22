package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zk.ui.Executions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardViewModel {

    private String filterText = "";
    private List<Creditor> filteredCreditors;

    public DashboardViewModel() {
        filteredCreditors = new ArrayList<>(CreditorService.getInstance().getCreditors());
    }

    public List<Creditor> getFilteredCreditors() {
        return filteredCreditors;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    @Command
    @NotifyChange("filteredCreditors")
    public void filter() {
        String lowerFilter = filterText.toLowerCase().trim();
        filteredCreditors = CreditorService.getInstance().getCreditors().stream()
                .filter(c -> String.valueOf(c.getId()).contains(lowerFilter)
                        || c.getName().toLowerCase().contains(lowerFilter)
                        || c.getStatus().toLowerCase().contains(lowerFilter))
                .collect(Collectors.toList());
    }

    @Command
    public void editCreditor(@BindingParam("id") int id) {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/detail.zul&id=" + id);
    }

    @Command
    @NotifyChange({"filteredCreditors", "filterText"})
    public void deleteCreditor(@BindingParam("id") int id) {
        CreditorService.getInstance().deleteCreditor(id);
        filter(); // refresh filtered list setelah delete
    }
}
