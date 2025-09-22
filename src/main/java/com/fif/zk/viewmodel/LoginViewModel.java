package com.fif.zk.viewmodel;

import com.fif.zk.model.User;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

public class LoginViewModel {

    private User user = new User();

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Command
    @NotifyChange("user")
    public void login() {
        Executions.sendRedirect("/pages/layout.zul?page=/pages/dashboard.zul");
    }
}
