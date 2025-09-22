package com.fif.zk.viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class LoginViewModel {

    private String username;
    private String password;

    // Getter & Setter
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Command
    public void login() {
        // Dummy login, tidak ada validasi
        Executions.sendRedirect("/pages/layout.zul?page=/pages/dashboard.zul");
    }
}
