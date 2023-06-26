package com.library.main.security;

import com.library.main.security.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.library.main.enums.Permission.*;
import static com.library.main.enums.Role.ADMIN;
import static com.library.main.enums.Role.MANAGER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class LibrarySecurityConfig {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Value("${urls.user}")
    private String[] USER_URLs;

    @Value("${urls.book}")
    private String[] BOOK_URLs;

    @Value("${urls.unsecured}")
    private String[] UN_SECURED_URLs;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(UN_SECURED_URLs).permitAll()
                .and()
                .authorizeHttpRequests().antMatchers(BOOK_URLs).hasAnyRole(ADMIN.name(),MANAGER.name())//access to users having admin and manager
                .antMatchers(GET,BOOK_URLs).hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())//users with read privilege
                .antMatchers(POST,BOOK_URLs).hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())//users with create privilege
                .and()
                /*.authorizeHttpRequests().antMatchers(USER_URLs).hasRole(ADMIN.name())//access only to admin users
                .antMatchers(POST,USER_URLs).hasAuthority(ADMIN_CREATE.name())
                .antMatchers(GET,USER_URLs).hasAuthority(ADMIN_READ.name())
                .anyRequest().authenticated()
                .and()*/
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .and()
                .build();
    }


}
