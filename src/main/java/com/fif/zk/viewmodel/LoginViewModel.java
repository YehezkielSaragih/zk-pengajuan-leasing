package com.fif.zk.viewmodel;

import com.fif.zk.model.User;
import com.fif.zk.service.UserService;
import com.fif.zk.service.implementation.UserServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

public class LoginViewModel {

    private User user = new User();
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    private boolean emailError;
    private boolean passwordError;
    public boolean isEmailError() { return emailError; }
    public boolean isPasswordError() { return passwordError; }
    public void setEmailError(boolean emailError) { this.emailError = emailError; }
    public void setPasswordError(boolean passwordError) { this.passwordError = passwordError; }

    private final UserService userService = UserServiceImpl.getInstance();

    @Command
    @NotifyChange({"user", "emailError", "passwordError"})
    public void login() {
        emailError = false;
        passwordError = false;

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            emailError = true;
            return;
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            passwordError = true;
            return;
        }

        if (userService.validateUser(user.getEmail(), user.getPassword())) {
            Executions.sendRedirect("/pages/layout.zul?page=/pages/creditor-dashboard.zul");
        } else {
            emailError = true;
            passwordError = true;
        }
    }

    @Command
    public void goToRegister() {
        Executions.sendRedirect("/pages/register.zul");
    }
}