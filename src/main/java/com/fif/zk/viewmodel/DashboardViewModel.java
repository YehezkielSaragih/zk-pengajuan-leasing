package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.BindingParam;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardViewModel {

    private String filterText = "";
    private String filterStatus = "All";
    private List<Creditor> filteredCreditors;

    private int deletingId = -1;
    private boolean showDeleteModal = false;

    public DashboardViewModel() {
        filteredCreditors = CreditorServiceImpl.getInstance().getCreditors();
    }

    // getters / setters
    public List<Creditor> getFilteredCreditors() { return filteredCreditors; }
    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }
    public String getFilterStatus() { return filterStatus; }
    public void setFilterStatus(String filterStatus) { this.filterStatus = filterStatus; }
    public int getDeletingId() { return deletingId; }
    public boolean isShowDeleteModal() { return showDeleteModal; }

    @Command
    @NotifyChange("filteredCreditors")
    public void filter() {
        filteredCreditors = CreditorServiceImpl.getInstance().getCreditors().stream()
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
        filteredCreditors = CreditorServiceImpl.getInstance().getCreditors();
    }

    @Command
    public void editCreditor(@BindingParam("id") int id) {
        // buka halaman detail dengan parameter id
        org.zkoss.zk.ui.Executions.sendRedirect("/pages/layout.zul?page=detail.zul?id=" + id);
    }

    @Command
    @NotifyChange("showDeleteModal")
    public void showDeleteModal(@BindingParam("id") int id) {
        deletingId = id;
        showDeleteModal = true; // munculkan modal
    }

    @Command
    @NotifyChange({"filteredCreditors", "showDeleteModal"})
    public void confirmDelete() {
        if (deletingId != -1) {
            CreditorServiceImpl.getInstance().deleteCreditor(deletingId);
            filter(); // refresh list
        }
        deletingId = -1;
        showDeleteModal = false;
    }

    @Command
    @NotifyChange("showDeleteModal")
    public void cancelDelete() {
        deletingId = -1;
        showDeleteModal = false;
    }
}
