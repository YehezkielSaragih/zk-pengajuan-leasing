package com.fif.zk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/pages/login.zul", "/pages/register.zul", "/css/**", "/js/**", "/images/**").permitAll()
                    .antMatchers(
                            "/pages/layout.zul?page=creditor-dashboard.zul",
                            "/pages/layout.zul?page=creditor-form.zul"
                    ).hasRole("USER")
                    .antMatchers(
                            "/pages/layout.zul?page=creditor-dashboard.zul",
                            "/pages/layout.zul?page=creditor-form.zul",
                            "/pages/layout.zul?page=loan-dashboard.zul",
                            "/pages/layout.zul?page=loan-form.zul"
                    ).hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/pages/login.zul")
                    .defaultSuccessUrl("/pages/layout.zul?page=creditor-dashboard.zul", true)
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/pages/login.zul")
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/pages/accessDenied.zul");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
