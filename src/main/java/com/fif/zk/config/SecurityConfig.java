package com.fif.zk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                .antMatchers(
                        "/pages/login.zul",
                        "/pages/register.zul",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/zkau/**",   // penting untuk ZK Ajax
                        "/zkres/**"   // penting untuk ZK resources (CSS, JS bawaan ZK)
                ).permitAll()
                // ADMIN akses penuh ke loan-dashboard & loan-form
                .antMatchers(
                        "/pages/layout.zul?page=loan-dashboard.zul",
                        "/pages/layout.zul?page=loan-form.zul"
                ).hasRole("ADMIN")
                // USER & ADMIN sama-sama boleh akses creditor
                .antMatchers(
                        "/pages/layout.zul?page=creditor-dashboard.zul",
                        "/pages/layout.zul?page=creditor-form.zul"
                ).hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/pages/login.zul")
                .loginProcessingUrl("/pages/login") // Spring Security POST URL
                .successHandler(customSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/pages/login.zul")  // cukup ini
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/pages/accessDenied.zul");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                response.sendRedirect("/zk-pengajuan-leasing/pages/layout.zul?page=creditor-dashboard.zul");
            } else {
                response.sendRedirect("/zk-pengajuan-leasing/pages/layout.zul?page=loan-dashboard.zul");
            }
        };
    }

}
