package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService users() throws Exception {
        List<UserDetails> users = List.of(
                User.withUsername("user1")
                        .password("{noop}user1")
                        .roles("USERS")
                        .build(),
                User.withUsername("admin")
                        .password("{noop}admin")
                        .roles("USERS", "ADMIN")
                        .build(),
                User.withUsername("user2")
                        .password("{noop}user2")
                        .roles("OPERATOR")
                        .build()
                );
        return new MapReactiveUserDetailsService(users);
    }

    @Bean
    public SecurityWebFilterChain filter(ServerHttpSecurity http) throws Exception {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth ->
                        auth.pathMatchers(HttpMethod.POST, "/productos").hasAnyRole("ADMIN")
                                .pathMatchers(HttpMethod.DELETE, "/productos/**").hasAnyRole("OPERATOR")
                                .pathMatchers(HttpMethod.GET,"/***").authenticated()
                                .anyExchange().permitAll())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
