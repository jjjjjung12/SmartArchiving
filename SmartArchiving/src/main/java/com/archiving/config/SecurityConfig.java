package com.archiving.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
import com.archiving.auth.security.MenuAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final AppLogoutSuccessHandler appLogoutSuccessHandler;
    private final LoginWebAuthenticationDetailsSource loginWebAuthenticationDetailsSource;
    private final MenuAuthorizationManager menuAuthorizationManager;

    public SecurityConfig(
            AuthenticationProvider authenticationProvider,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler,
            AppLogoutSuccessHandler appLogoutSuccessHandler,
            LoginWebAuthenticationDetailsSource loginWebAuthenticationDetailsSource,
            MenuAuthorizationManager menuAuthorizationManager) {
        this.authenticationProvider = authenticationProvider;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.appLogoutSuccessHandler = appLogoutSuccessHandler;
        this.loginWebAuthenticationDetailsSource = loginWebAuthenticationDetailsSource;
        this.menuAuthorizationManager = menuAuthorizationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests(auth -> auth
                .antMatchers(
                    "/login",
                    "/login.jsp",
                    "/health",
                    "/sso/**",
                    "/userAuthApplyLogin",
                    "/userAuthApplyLogin.jsp",
                    "/userApproveProcNonLogin",
                    "/userApproveProcNonLogin.jsp",
                    "/noAuth",
                    "/noAuth.jsp",
                    "/GetLogin",
                    "/GetUserAuthApply",
                    "/SetUserAuthApply",
                    "/GetUserApproveProcList",
                    "/SetUserApproveProc",
                    "/js/**",
                    "/css/**",
                    "/assets/**",
                    "/images/**",
                    "/font/**",
                    "/fonts/**",
                    "/error"
                ).permitAll()
                .anyRequest().access(menuAuthorizationManager)
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
                .ignoringAntMatchers("/GetLogin")
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    if (MenuAuthorizationManager.isApiRequest(request.getServletPath(), request)) {
                        response.setStatus(403);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"result\":\"loginRequired\",\"message\":\"세션이 만료되었습니다. 로그인이 필요합니다.\"}");
                        return;
                    }
                    response.sendRedirect(request.getContextPath() + "/login");
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    if (MenuAuthorizationManager.isApiRequest(request.getServletPath(), request)) {
                        response.setStatus(403);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write("{\"result\":\"noAuth\",\"message\":\"메뉴 권한이 없습니다.\"}");
                        return;
                    }
                    response.sendRedirect(request.getContextPath() + "/noAuth");
                })
            );

        return http.build();
    }
}
