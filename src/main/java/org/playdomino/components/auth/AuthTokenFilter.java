package org.playdomino.components.auth;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.services.auth.token.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Getter
@RequiredArgsConstructor
public final class AuthTokenFilter extends OncePerRequestFilter {
    private final AccessTokenService accessTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (Objects.nonNull(jwt) && getAccessTokenService().isValid(jwt)) {
                User user = (User) getUserDetailsService().loadUserByUsername(getAccessTokenService().id(jwt));
                UsernamePasswordAuthenticationToken authentication = user.newAuthenticationToken(null);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (Objects.nonNull(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        } else {
            return headerAuth;
        }
    }
}