package org.playdomino.components.auth;

import lombok.extern.log4j.Log4j2;
import org.playdomino.models.auth.User;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

@Log4j2
public final class UserUtils {
    public static User currentUser() {
        return (User) Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal).orElse(null);
    }

    public static User currentUser(SimpMessageHeaderAccessor accessor) {
        UsernamePasswordAuthenticationToken user = ((UsernamePasswordAuthenticationToken) accessor.getUser());
        if (Objects.isNull(user)) {
            log.error("User is null for accessor {}", accessor);
            throw new BadCredentialsException("User is null for accessor");
        }
        return (User) user.getPrincipal();
    }
}
