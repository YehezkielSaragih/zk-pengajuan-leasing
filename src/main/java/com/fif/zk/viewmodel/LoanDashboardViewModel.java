package com.fif.zk.viewmodel;

import com.fif.zk.dto.LoanDashboardResponse;
import com.fif.zk.model.Creditor;
import com.fif.zk.service.CreditorService;
import com.fif.zk.service.LoanService;
import com.fif.zk.service.LoanTypeService;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class LoanDashboardViewModel {

    // --- Filters ---
    private String filterText = "";
    private String filterStatus = "All Status";
    private String filterType = "All Loan Type";

    // --- State ---
    private List<LoanDashboardResponse> filteredLoans;
    private int deletingId = -1;
    private boolean showDeleteModal = false;

    // --- Services ---
    @WireVariable("creditorServiceImpl")
    private CreditorService creditorService;

    @WireVariable("loanServiceImpl")
    private LoanService loanService;

    @WireVariable("loanTypeServiceImpl")
    private LoanTypeService loanTypeService;

    // --- Data for dropdown ---
    private List<String> loanTypeOptions;

    @Init
    public void init() {
        filteredLoans = loadDashboardItems();

        // isi dropdown loan types dari DB + prepend "All Loan Type"
        loanTypeOptions = loanTypeService.getAllLoanTypes().stream()
                .map(lt -> lt.getName())
                .collect(Collectors.toList());

        loanTypeOptions.add(0, "All Loan Type");
    }

    @AfterCompose
    public void afterCompose(@BindingParam("comp") Component comp) {
        String success = Executions.getCurrent().getParameter("success");
        if ("true".equals(success)) {
            Clients.showNotification("Loan saved successfully!", "info", null, "top_right", 3000);
        }
    }

    public List<String> getLoanTypeOptions() {
        return loanTypeOptions;
    }

    // --- Getters & Setters ---
    public String getFilterText() { return filterText; }
    public void setFilterText(String filterText) { this.filterText = filterText; }

    public String getFilterStatus() { return filterStatus; }
    public void setFilterStatus(String filterStatus) { this.filterStatus = filterStatus; }

    public String getFilterType() { return filterType; }
    public void setFilterType(String filterType) { this.filterType = filterType; }

    public List<LoanDashboardResponse> getFilteredLoans() { return filteredLoans; }

    public int getDeletingId() { return deletingId; }
    public boolean isShowDeleteModal() { return showDeleteModal; }

    // --- Summary Fields ---
    public int getTotalLoans() { return filteredLoans.size(); }

    public int getApprovedLoans() {
        return (int) filteredLoans.stream().filter(l -> "Approved".equalsIgnoreCase(l.getStatus())).count();
    }

    public int getPendingLoans() {
        return (int) filteredLoans.stream().filter(l -> "Pending".equalsIgnoreCase(l.getStatus())).count();
    }

    public int getRejectedLoans() {
        return (int) filteredLoans.stream().filter(l -> "Rejected".equalsIgnoreCase(l.getStatus())).count();
    }

    public String getTotalLoanAmountFormatted() {
        int totalAmount = filteredLoans.stream().mapToInt(LoanDashboardResponse::getLoanAmount).sum();
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return nf.format(totalAmount);
    }

    // --- Private Helpers ---
    private List<LoanDashboardResponse> loadDashboardItems() {
        return loanService.getLoans().stream()
                .map(l -> {
                    Creditor c = creditorService.getCreditorById(l.getCreditor().getId());
                    return new LoanDashboardResponse(
                            l.getId(),
                            c != null ? c.getName() : "Unknown",
                            l.getLoanName(),
                            l.getLoanType().getName(),
                            l.getLoanAmount(),
                            l.getDownPayment(),
                            l.getStatus().name()
                    );
                })
                .sorted(Comparator.comparing(LoanDashboardResponse::getLoanId))
                .collect(Collectors.toList());
    }

    // --- Commands ---
    @Command
    @NotifyChange({"filteredLoans", "totalLoans", "approvedLoans", "pendingLoans", "rejectedLoans", "totalLoanAmountFormatted"})
    public void filter() {
        filteredLoans = loadDashboardItems().stream()
                .filter(l -> (filterText.isEmpty()
                        || l.getCreditorName().toLowerCase().contains(filterText.toLowerCase())
                        || String.valueOf(l.getLoanId()).contains(filterText)))
                .filter(l -> filterStatus.equals("All Status") || l.getStatus().equalsIgnoreCase(filterStatus))
                .filter(l -> filterType.equals("All Loan Type") || l.getLoanType().equalsIgnoreCase(filterType))
                .collect(Collectors.toList());
    }

    @Command
    @NotifyChange({"filteredLoans", "filterText", "filterStatus", "filterType",
            "totalLoans", "approvedLoans", "pendingLoans", "rejectedLoans", "totalLoanAmountFormatted"})
    public void clearFilter() {
        filterText = "";
        filterStatus = "All Status";
        filterType = "All Loan Type";
        filteredLoans = loadDashboardItems();
    }

    @Command
    public void editLoan(@BindingParam("id") int id) {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/loan-detail.zul&id=" + id);
    }

    @Command
    @NotifyChange("showDeleteModal")
    public void showDeleteModal(@BindingParam("id") int id) {
        deletingId = id;
        showDeleteModal = true;
    }

    @Command
    @NotifyChange({"filteredLoans", "showDeleteModal", "totalLoans", "approvedLoans", "pendingLoans", "rejectedLoans", "totalLoanAmountFormatted"})
    public void confirmDelete() {
        if (deletingId != -1) {
            loanService.deleteLoan(deletingId);
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
    public void goToAddLoan() {
        Executions.sendRedirect("/pages/layout.zul?page=loan-form.zul");
    }
}