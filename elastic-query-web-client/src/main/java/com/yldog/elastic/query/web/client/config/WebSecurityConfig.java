package com.yldog.elastic.query.web.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class WebSecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // t means we will not secure the URLs having one of these pattern
        return (web) -> web.ignoring()
                .requestMatchers("/**");
    }
}
