package com.fif.zk.viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

public class LayoutViewModel {
    private String page;

    @Init
    public void init() {
        // Ambil parameter ?page= dari URL
        String p = Executions.getCurrent().getParameter("page");
        if (p != null && !p.isEmpty()) {
            page = p;
        } else {
            page = "home.zul"; // default halaman
        }
    }

    public String getPage() {
        return page;
    }
}
