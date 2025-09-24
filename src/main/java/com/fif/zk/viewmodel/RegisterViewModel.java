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
public class RegisterViewModel {

    // --- Data fields ---
    private User user = new User();
    private String confirmPassword;

    // --- Error flags ---
    private boolean emailError = false;
    private boolean passwordError = false;
    private boolean confirmError = false;

    // --- Service ---
    @WireVariable("userServiceImpl")
    private UserService userService;

    // --- Getters & Setters ---
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public boolean isEmailError() { return emailError; }
    public boolean isPasswordError() { return passwordError; }
    public boolean isConfirmError() { return confirmError; }

    // --- Commands ---
    @Command
    @NotifyChange({"emailError", "passwordError", "confirmError"})
    public void register() {
        // reset errors
        emailError = false;
        passwordError = false;
        confirmError = false;

        boolean valid = true;

        // --- validate email ---
        String email = user.getEmail();
        if (email == null || email.trim().isEmpty()) {
            emailError = true;
            valid = false;
        } else {
            // cek format email
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            if (!email.matches(emailRegex)) {
                emailError = true;
                valid = false;
            } else if (userService.existsEmail(email.trim())) {
                // cek email sudah terdaftar
                emailError = true;
                valid = false;
            }
        }

        // validate password
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            passwordError = true;
            valid = false;
        }

        // validate confirm password
        if (!user.getPassword().equals(confirmPassword)) {
            confirmError = true;
            valid = false;
        }

        if (!valid) return;

        // set default role
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("ADMIN");
        }

        // persist user & redirect
        userService.addUser(user);
        Executions.sendRedirect("/pages/login.zul");
    }

    @Command
    public void goToLogin() {
        Executions.sendRedirect("/pages/login.zul");
    }
}