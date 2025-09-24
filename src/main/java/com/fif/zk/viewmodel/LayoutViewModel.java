package com.fif.zk.viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class LayoutViewModel {

    private String page;
    private boolean isCreditorDashboard = false;
    private boolean isLoanDashboard = false;
    private boolean isCreditorForm = false;
    private boolean isLoanForm = false;

    @Init
    public void init() {
        String p = Executions.getCurrent().getParameter("page");
        if (p != null && !p.isEmpty()) {
            if (!p.startsWith("/pages/")) {
                page = "/pages/" + p;
            } else {
                page = p;
            }
        } else {
            page = "/pages/creditor-dashboard.zul"; // default halaman
        }

        if (page.contains("creditor-dashboard")) {
            isCreditorDashboard = true;
        } else if (page.contains("loan-dashboard")) {
            isLoanDashboard = true;
        } else if (page.contains("creditor-form")) {
            isCreditorForm = true;
        } else if (page.contains("loan-form")) {
            isLoanForm = true;
        }
    }

    public String getPage() {
        return page;
    }

    public String getCreditorDashboardClass() {
        return isCreditorDashboard ? "sidebar-link active" : "sidebar-link";
    }

    public String getLoanDashboardClass() {
        return isLoanDashboard ? "sidebar-link active" : "sidebar-link";
    }

    public String getCreditorFormClass() {
        return isCreditorForm ? "sidebar-link active" : "sidebar-link";
    }

    public String getLoanFormClass() {
        return isLoanForm ? "sidebar-link active" : "sidebar-link";
    }
}
