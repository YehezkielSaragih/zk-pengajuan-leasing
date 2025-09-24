package com.fif.zk.viewmodel;

import com.fif.zk.dto.CreditorDashboardResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.implementation.CreditorServiceImpl;
import com.fif.zk.service.implementation.LoanServiceImpl;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreditorDashboardViewModel {

    private String filterText = "";
    private List<CreditorDashboardResponse> filteredCreditors;

    private int deletingId = -1;
    private boolean showDeleteModal = false;

    public CreditorDashboardViewModel() {
        filteredCreditors = loadDashboardItems();
    }

    public List<CreditorDashboardResponse> getFilteredCreditors() { return filteredCreditors; }
    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }
    public int getDeletingId() { return deletingId; }
    public boolean isShowDeleteModal() { return showDeleteModal; }

    private List<CreditorDashboardResponse> loadDashboardItems() {
        return CreditorServiceImpl.getInstance().getCreditors().stream()
                .map(c -> {
                    List<Loan> loans = LoanServiceImpl.getInstance().getLoansByCreditorId(c.getId())
                            .stream()
                            .filter(l -> l.getDeletedAt() == null)  // exclude soft-deleted
                            .collect(Collectors.toList());

                    long active = loans.stream().filter(l -> "Approved".equalsIgnoreCase(l.getStatus())).count();
                    long rejected = loans.stream().filter(l -> "Rejected".equalsIgnoreCase(l.getStatus())).count();
                    long pending = loans.stream().filter(l -> "Pending".equalsIgnoreCase(l.getStatus())).count();


                    return new CreditorDashboardResponse(
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
        Executions.sendRedirect("/pages/layout.zul?page=/pages/creditor-detail.zul&id=" + id);
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

    private boolean showLoanModal = false;
    private String selectedCreditorName;
    private List<Loan> selectedLoans = new ArrayList<>();

    public boolean isShowLoanModal() { return showLoanModal; }
    public String getSelectedCreditorName() { return selectedCreditorName; }
    public List<Loan> getSelectedLoans() { return selectedLoans; }

    @Command
    @NotifyChange({"showLoanModal", "selectedLoans", "selectedCreditorName"})
    public void viewLoans(@BindingParam("id") int creditorId) {
        // ambil creditor
        Creditor c = CreditorServiceImpl.getInstance().getCreditors().stream()
                .filter(cred -> cred.getId() == creditorId)
                .findFirst()
                .orElse(null);

        if (c != null) {
            selectedCreditorName = c.getName();
            selectedLoans = LoanServiceImpl.getInstance().getLoansByCreditorId(c.getId())
                    .stream()
                    .filter(l -> l.getDeletedAt() == null)
                    .collect(Collectors.toList());
            showLoanModal = true;
        }
    }

    @Command
    @NotifyChange("showLoanModal")
    public void closeLoanModal() {
        showLoanModal = false;
    }
}
