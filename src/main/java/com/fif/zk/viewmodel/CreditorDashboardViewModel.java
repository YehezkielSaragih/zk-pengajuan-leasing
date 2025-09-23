package com.fif.zk.viewmodel;

import com.fif.zk.dto.CreditorDashboardItem;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.util.List;
import java.util.stream.Collectors;

public class CreditorDashboardViewModel {
    private String filterText = "";
    private List<CreditorDashboardItem> filteredCreditors;

    private int deletingId = -1;
    private boolean showDeleteModal = false;

    public CreditorDashboardViewModel() {
        filteredCreditors = loadDashboardItems();
    }

    // === Getters & Setters ===
    public List<CreditorDashboardItem> getFilteredCreditors() { return filteredCreditors; }
    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }
    public int getDeletingId() { return deletingId; }
    public boolean isShowDeleteModal() { return showDeleteModal; }

    // === Load data ===
    private List<CreditorDashboardItem> loadDashboardItems() {
        return CreditorServiceImpl.getInstance().getCreditors().stream()
                .map(c -> {
                    List<Loan> loans = LoanServiceImpl.getInstance().getLoansByCreditorId(c.getId())
                            .stream()
                            .filter(l -> l.getDeletedAt() == null)  // exclude soft-deleted
                            .collect(Collectors.toList());

                    long active = loans.stream().filter(l -> "Approved".equalsIgnoreCase(l.getStatus())).count();
                    long rejected = loans.stream().filter(l -> "Rejected".equalsIgnoreCase(l.getStatus())).count();
                    long pending = loans.stream().filter(l -> "Pending".equalsIgnoreCase(l.getStatus())).count();


                    return new CreditorDashboardItem(
                            c.getId(),
                            c.getName(),
                            c.getIncome(),
                            active,
                            rejected,
                            pending
                    );
                })
                .collect(Collectors.toList());
    }

    // === Commands ===
    @Command
    @NotifyChange("filteredCreditors")
    public void filter() {
        filteredCreditors = loadDashboardItems().stream()
                .filter(c -> filterText.isEmpty()
                        || c.getName().toLowerCase().contains(filterText.toLowerCase())
                        || String.valueOf(c.getId()).contains(filterText))
                .collect(Collectors.toList());
    }

    @Command
    @NotifyChange({"filteredCreditors", "filterText"})
    public void clearFilter() {
        filterText = "";
        filteredCreditors = loadDashboardItems();
    }

    @Command
    public void editCreditor(@BindingParam("id") int id) {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditorDetail.zul&id=" + id);
    }

    @Command
    @NotifyChange("showDeleteModal")
    public void showDeleteModal(@BindingParam("id") int id) {
        deletingId = id;
        showDeleteModal = true;
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
