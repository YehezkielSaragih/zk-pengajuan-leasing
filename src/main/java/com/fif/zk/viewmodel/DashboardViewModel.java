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
    private String filterStatus = "All";
    private List<Creditor> filteredCreditors;

    public DashboardViewModel() {
        filteredCreditors = CreditorService.getInstance().getCreditors();
    }

    public List<Creditor> getFilteredCreditors() {
        return filteredCreditors;
    }

    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }

    public String getFilterStatus() { return filterStatus; }
    public void setFilterStatus(String filterStatus) { this.filterStatus = filterStatus; }

    @Command
    @NotifyChange("filteredCreditors")
    public void filter() {
        filteredCreditors = CreditorService.getInstance().getCreditors().stream()
                .filter(c -> (filterText.isEmpty() || c.getName().toLowerCase().contains(filterText.toLowerCase())
                        || String.valueOf(c.getId()).contains(filterText)))
                .filter(c -> filterStatus.equals("All") || c.getStatus().equalsIgnoreCase(filterStatus))
                .collect(Collectors.toList());
    }

    @Command
    @NotifyChange({"filteredCreditors", "filterText", "filterStatus"})
    public void clearFilter() {
        filterText = "";
        filterStatus = "All";
        filteredCreditors = CreditorService.getInstance().getCreditors();
    }

    @Command
    public void editCreditor(@BindingParam("id") int id) {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/detail.zul&id=" + id);
    }

    @Command
    @NotifyChange({"filteredCreditors", "filterText"})
    public void deleteCreditor(@BindingParam("id") int id) {
        CreditorService.getInstance().deleteCreditor(id);
        filter();
    }
}
