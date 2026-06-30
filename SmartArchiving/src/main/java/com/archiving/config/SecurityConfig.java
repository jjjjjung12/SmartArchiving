package com.archiving.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.archiving.auth.handler.AppLogoutSuccessHandler;
import com.archiving.auth.handler.LoginFailureHandler;
import com.archiving.auth.handler.LoginSuccessHandler;
import com.archiving.auth.security.LoginWebAuthenticationDetailsSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final AppLogoutSuccessHandler appLogoutSuccessHandler;
    private final LoginWebAuthenticationDetailsSource loginWebAuthenticationDetailsSource;

    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            AppLogoutSuccessHandler appLogoutSuccessHandler,
            LoginWebAuthenticationDetailsSource loginWebAuthenticationDetailsSource) {
        this.authenticationProvider = authenticationProvider;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.appLogoutSuccessHandler = appLogoutSuccessHandler;
        this.loginWebAuthenticationDetailsSource = loginWebAuthenticationDetailsSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider)
            .authorizeRequests(auth -> auth
                .antMatchers(
                    "/login",
                    "/health",
                    "/sso/**",
                    "/userAuthApplyLogin",
                    "/userApproveProcNonLogin",
                    "/js/**",
                    "/css/**",
                    "/assets/**",
                    "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .authenticationDetailsSource(loginWebAuthenticationDetailsSource)
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessHandler(appLogoutSuccessHandler)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );

        return http.build();
    }
}
