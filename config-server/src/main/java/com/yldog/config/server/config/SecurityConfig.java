package com.yldog.config.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // t means we will not secure the URLs having one of these pattern
        return (web) -> web.ignoring()
                .requestMatchers("/actuator/**")
                .requestMatchers("/encrypt/**")
                .requestMatchers("/decrypt/**");
    }
}
