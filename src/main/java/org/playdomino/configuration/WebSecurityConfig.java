package org.playdomino.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.playdomino.components.auth.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Data
public class WebSecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(getUserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    MvcRequestMatcher.Builder mvc(
            HandlerMappingIntrospector introspector
    ) {
        return new MvcRequestMatcher.Builder(introspector);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security,
            AuthTokenFilter authTokenFilter
    ) throws Exception {
        security
                .cors((cors) -> {
                })
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(getAuthenticationEntryPoint())))
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((http) -> http
                        // HealthController
                        .requestMatchers("/v1/health-check").permitAll()
                        // AuthController
                        .requestMatchers("/v1/auth/**").permitAll()
                        // Any Other Request
                        .anyRequest().authenticated());

        security.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
}
