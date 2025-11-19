package com.example.demo;

import com.example.demo.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {

    private AuthManager authenticationManager;
    private SecurityContextRepository securityContextRepository;
    private UserDetailService userDetailService;

    public SecurityConfig(AuthManager authenticationManager,
                          SecurityContextRepository securityContextRepository,
                          UserDetailService userDetailService) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.userDetailService = userDetailService;
    }

    @Bean
    public ReactiveUserDetailsService users() throws Exception {
        return username -> userDetailService.findByUserName(username);
    }

    @Bean
    public SecurityWebFilterChain filter(ServerHttpSecurity http) throws Exception {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(auth ->
                        auth.pathMatchers(HttpMethod.POST, "/productos").hasAnyRole("ADMIN")
                                .pathMatchers(HttpMethod.DELETE, "/productos/**").hasAnyRole("OPERATOR")
                                .pathMatchers(HttpMethod.GET,"/**").authenticated()
                                .anyExchange().permitAll())
                .build();
    }
}
