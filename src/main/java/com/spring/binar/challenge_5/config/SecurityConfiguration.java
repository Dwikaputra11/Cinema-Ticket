package com.spring.binar.challenge_5.config;

import com.spring.binar.challenge_5.models.Role;
import com.spring.binar.challenge_5.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(configure -> configure
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/schedule/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/payment/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/payment/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/payment/**").hasAnyRole(Role.STAFF.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/payment/**").hasAnyRole(Role.STAFF.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/api/user/**").hasAnyRole(Role.STAFF.name(), Role.ADMIN.name())
                        .requestMatchers("api/user/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/film/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/studio/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/api/staff/**").hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
