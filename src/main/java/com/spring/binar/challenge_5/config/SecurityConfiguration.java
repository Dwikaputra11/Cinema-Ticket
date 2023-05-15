package com.spring.binar.challenge_5.config;

import com.spring.binar.challenge_5.models.Role;
import com.spring.binar.challenge_5.security.JwtAuthEntryPoint;
import com.spring.binar.challenge_5.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final JwtAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**", "/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/schedule/**","/api/payment/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/payment").permitAll()
                .requestMatchers("/api/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers(HttpMethod.POST, "/api/payment/**").hasAnyAuthority(Role.STAFF.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE, "/api/payment/**").hasAnyAuthority(Role.STAFF.name(), Role.ADMIN.name())
                .requestMatchers(HttpMethod.GET,"/api/user/**").hasAnyAuthority(Role.STAFF.name(), Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authProvider)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



}
