package com.spring.binar.challenge_5.config;

import com.spring.binar.challenge_5.models.Role;
import com.spring.binar.challenge_5.security.JwtAuthEntryPoint;
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
    private final JwtAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/auth/**", "/error","/web-public/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/schedule/**","/api/payment/**", "/api/film/**", "/api/user/*").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/payment").authenticated()
                                .requestMatchers("/web-public/schedule/**").authenticated()
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.GET, "/api/payment","/api/staff/*")
                                .hasAnyAuthority(Role.STAFF.name(), Role.ADMIN.name())
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers(HttpMethod.GET, "/api/costumer").hasAuthority(Role.ADMIN.name())
                                .requestMatchers("/api/costumer/**").hasAnyAuthority(Role.ADMIN.name(), Role.COSTUMER.name())
                )
                .authorizeHttpRequests(request -> request.requestMatchers("/api/**").hasAuthority(Role.ADMIN.name()))
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .formLogin().loginPage("/web-public/auth/login-form")
                .and()
                .authenticationProvider(authProvider)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .clearAuthentication(true)
                .logoutUrl("/authLogout")
                .logoutSuccessUrl("/web-public/auth/login-form").permitAll();

        return http.build();
    }



}
