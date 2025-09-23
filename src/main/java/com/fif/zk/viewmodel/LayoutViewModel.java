package com.fif.zk.viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class LayoutViewModel {
    private String page;
    private boolean isDashboard = false;
    private boolean isForm = false;

    @Init
    public void init() {
        // Ambil parameter ?page= dari URL
        String p = Executions.getCurrent().getParameter("page");
        if (p != null && !p.isEmpty()) {
            // Tambahkan prefix /pages/ jika belum ada
            if (!p.startsWith("/pages/")) {
                page = "/pages/" + p;
            } else {
                page = p;
            }
        } else {
            page = "/pages/dashboard.zul"; // default halaman
        }
        if (page.contains("dashboard")) {
            isDashboard = true;
        } else if (page.contains("form")) {
            isForm = true;
        }
    }

    public String getPage() {
        return page;
    }

    public String getDashboardClass() {
        return isDashboard ? "sidebar-link active" : "sidebar-link";
    }

    public String getFormClass() {
        return isForm ? "sidebar-link active" : "sidebar-link";
    }
}
