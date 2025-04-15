package org.playdomino.models.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.auth.AuthProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class UserToken {

    @Pattern(
            regexp = "^(?!.*[._]{2})(?!.*[._]$)[a-zA-Z][a-zA-Z0-9._]{2,31}$",
            message = "{validation.user.username.pattern}"
    )
    private String username;
    @Email
    private String email;
    @Pattern(
            regexp = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$",
            message = "{validation.user.refresh-token.pattern}"
    )
    private String refreshToken;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,128}$",
            message = "{validation.user.password.pattern}"
    )
    private String password;

    @JsonIgnore
    @SuppressWarnings("unused")
    @NotNull(message = "{validation.user.login.provider-not-found}")
    public AuthProvider getAuthProvider() {
        return AuthProvider.valueOf(this);
    }

    @JsonIgnore
    public Authentication getAuthentication() {
        return new UsernamePasswordAuthenticationToken(Optional.ofNullable(username).orElse(email), password);
    }
}
