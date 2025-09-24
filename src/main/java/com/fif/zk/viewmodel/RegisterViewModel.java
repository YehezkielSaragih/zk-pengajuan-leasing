package com.fif.zk.viewmodel;

import com.fif.zk.model.User;
import com.fif.zk.service.UserService;
import com.fif.zk.service.implementation.UserServiceImpl;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

public class RegisterViewModel {

    private User user = new User();
    private String confirmPassword;

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    private boolean emailError = false;
    private boolean passwordError = false;
    private boolean confirmError = false;

    public boolean isEmailError() { return emailError; }
    public boolean isPasswordError() { return passwordError; }
    public boolean isConfirmError() { return confirmError; }

    private final UserService userService = UserServiceImpl.getInstance();

    @Command
    @NotifyChange({"emailError", "passwordError", "confirmError"})
    public void register() {
        emailError = false;
        passwordError = false;
        confirmError = false;

        boolean valid = true;

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            emailError = true;
            valid = false;
        } else if (userService.existsEmail(user.getEmail().trim())) {
            emailError = true;
            valid = false;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            passwordError = true;
            valid = false;
        }

        if (!user.getPassword().equals(confirmPassword)) {
            confirmError = true;
            valid = false;
        }

        if (!valid) return;

        userService.addUser(user);
        Executions.sendRedirect("/pages/login.zul");
    }

    @Command
    public void goToLogin() {
        Executions.sendRedirect("/pages/login.zul");
    }
}