package org.playdomino.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.playdomino.components.auth.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

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
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity security,
            AuthTokenFilter authTokenFilter,
            CorsConfigurationSource corsConfigurationSource
    ) throws Exception {
        security
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling((httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(getAuthenticationEntryPoint())))
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((http) -> http
                        // HealthController
                        .requestMatchers("/v1/health-check").permitAll()
                        // WebSocket endpoint
                        .requestMatchers("/ws").permitAll()
                        // FAQController
                        .requestMatchers("/v1/faq").permitAll()
                        // AuthController
                        .requestMatchers("/v1/auth/**").permitAll()
                        .requestMatchers("/v1/wallet/*/confirm").hasRole("ADMIN")
                        .requestMatchers("/v1/notification").hasRole("ADMIN")
                        // Any Other Request
                        .anyRequest().authenticated())
                .httpBasic(AbstractHttpConfigurer::disable);

        security.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return security.build();
    }
}
