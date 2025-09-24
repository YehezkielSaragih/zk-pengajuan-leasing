package com.fif.zk.viewmodel;

import com.fif.zk.model.User;
import com.fif.zk.service.UserService;
import org.springframework.stereotype.Component;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@Component
@VariableResolver(DelegatingVariableResolver.class)
public class LoginViewModel {

    // --- Data fields ---
    private User user = new User();

    // --- Error flags ---
    private boolean emailError;
    private boolean passwordError;

    // --- Service ---
    @WireVariable("userServiceImpl")
    private UserService userService;

    // --- Getters & Setters ---
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isEmailError() { return emailError; }
    public boolean isPasswordError() { return passwordError; }

    public void setEmailError(boolean emailError) { this.emailError = emailError; }
    public void setPasswordError(boolean passwordError) { this.passwordError = passwordError; }

    // --- Commands ---
    @Command
    @NotifyChange({"user", "emailError", "passwordError"})
    public void login() {
        // reset errors
        emailError = false;
        passwordError = false;

        // validate email
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            emailError = true;
            return;
        }

        // validate password
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            passwordError = true;
            return;
        }

        // check user
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