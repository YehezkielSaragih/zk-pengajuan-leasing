package com.fif.zk.viewmodel;

import com.fif.zk.model.Creditor;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import com.fif.zk.dto.LoanDashboardItem;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.zk.ui.Executions;

import java.util.List;
import java.util.stream.Collectors;

public class LoanDashboardViewModel {

    private String filterText = "";
    private String filterStatus = "All";
    private List<LoanDashboardItem> filteredLoans;

    private int deletingId = -1;
    private boolean showDeleteModal = false;

    public LoanDashboardViewModel() {
        filteredLoans = loadDashboardItems();
    }

    // === Getters ===
    public List<LoanDashboardItem> getFilteredLoans() { return filteredLoans; }
    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }
    public String getFilterStatus() { return filterStatus; }
    public void setFilterStatus(String filterStatus) { this.filterStatus = filterStatus; }
    public int getDeletingId() { return deletingId; }
    public boolean isShowDeleteModal() { return showDeleteModal; }

    private List<LoanDashboardItem> loadDashboardItems() {
        return LoanServiceImpl.getInstance().getLoans().stream()
                .map(l -> {
                    Creditor c = CreditorServiceImpl.getInstance().getCreditorById(l.getCreditorId());
                    return new LoanDashboardItem(
                            l.getId(),
                            c != null ? c.getName() : "Unknown",
                            l.getLoanName(),
                            l.getLoanType(),
                            l.getLoanAmount(),
                            l.getDownPayment(),
                            l.getStatus()
                    );
                })
                .collect(Collectors.toList());
    }

    @Command
    @NotifyChange("filteredLoans")
    public void filter() {
        filteredLoans = loadDashboardItems().stream()
                .filter(l -> (filterText.isEmpty()
                        || l.getCreditorName().toLowerCase().contains(filterText.toLowerCase())
                        || String.valueOf(l.getLoanId()).contains(filterText)))
                .filter(l -> filterStatus.equals("All") || l.getStatus().equalsIgnoreCase(filterStatus))
                .collect(Collectors.toList());
    }

    @Command
    @NotifyChange({"filteredLoans", "filterText", "filterStatus"})
    public void clearFilter() {
        filterText = "";
        filterStatus = "All";
        filteredLoans = loadDashboardItems();
    }

    @Command
    public void editLoan(@BindingParam("id") int id) {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loanDetail.zul&id=" + id);
    }

    @Command
    @NotifyChange("showDeleteModal")
    public void showDeleteModal(@BindingParam("id") int id) {
        deletingId = id;
        showDeleteModal = true;
    }

    @Command
    @NotifyChange({"filteredLoans", "showDeleteModal"})
    public void confirmDelete() {
        if (deletingId != -1) {
            LoanServiceImpl.getInstance().deleteLoan(deletingId);
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
