package com.fif.zk.viewmodel;

import com.fif.zk.dto.CreditorDashboardResponse;
import com.fif.zk.dto.LoanDashboardResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.model.Loan;
import com.fif.zk.service.CreditorService;
import com.fif.zk.service.LoanService;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class CreditorDashboardViewModel {

    // --- Services ---
    @WireVariable("creditorServiceImpl")
    private CreditorService creditorService;

    @WireVariable("loanServiceImpl")
    private LoanService loanService;

    // --- Fields ---
    private String filterText = "";
    private List<CreditorDashboardResponse> filteredCreditors;

    private int deletingId = -1;
    private boolean showDeleteModal = false;

    private boolean showLoanModal = false;
    private String selectedCreditorName;
    private List<LoanDashboardResponse> selectedLoans = new ArrayList<>();

    // --- Init ---
    @Init
    public void init() {
        filteredCreditors = loadDashboardItems();
    }

    // --- Getters & Setters ---
    public List<CreditorDashboardResponse> getFilteredCreditors() { return filteredCreditors; }
    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }
    public int getDeletingId() { return deletingId; }
    public boolean isShowDeleteModal() { return showDeleteModal; }
    public boolean isShowLoanModal() { return showLoanModal; }
    public String getSelectedCreditorName() { return selectedCreditorName; }
    public List<LoanDashboardResponse> getSelectedLoans() { return selectedLoans; }


    // --- Private Methods ---
    private List<CreditorDashboardResponse> loadDashboardItems() {
        return creditorService.getCreditors().stream()
                .map(c -> {
                    List<Loan> loans = loanService.getLoansByCreditorId(c.getId())
                            .stream()
                            .filter(l -> l.getDeletedAt() == null) // exclude soft-deleted
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
                .sorted(Comparator.comparing(CreditorDashboardResponse::getId))
                .collect(Collectors.toList());
    }

    // --- Commands ---
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
            creditorService.deleteCreditor(deletingId);
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

    @Command
    @NotifyChange({"showLoanModal", "selectedLoans", "selectedCreditorName"})
    public void viewLoans(@BindingParam("creditor") CreditorDashboardResponse creditorDto) {
        if (creditorDto != null) {
            selectedCreditorName = creditorDto.getName();

            selectedLoans = loanService.getLoansByCreditorId(creditorDto.getId())
                    .stream()
                    .filter(l -> l.getDeletedAt() == null)
                    .map(l -> new LoanDashboardResponse(
                            l.getId(),
                            creditorDto.getName(),
                            l.getLoanName(),
                            l.getLoanType(),
                            l.getLoanAmount(),
                            l.getDownPayment(),
                            l.getStatus()
                    ))
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