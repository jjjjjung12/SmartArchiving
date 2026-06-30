package com.archiving.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
public class AuthSecurityBeansConfig {

    @Bean
    public AuthenticationProvider authenticationProvider(ArchivingAuthenticationProvider provider) {
        return provider;
    }
}
