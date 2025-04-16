package org.playdomino.models.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.Role;
import org.playdomino.models.auth.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class UserCreate {
    @NotNull
    private Country country;
    @NotEmpty
    private String federalDocument;
    @Email
    private String email;
    @NotNull
    @Pattern(
            regexp = "^(?!.*[._]{2})(?!.*[._]$)[a-zA-Z][a-zA-Z0-9._]{2,31}$",
            message = "{validation.user.username.pattern}"
    )
    private String username;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,128}$",
            message = "{validation.user.password.pattern}"
    )
    private String password;

    @JsonIgnore
    public User asUser(
            PasswordEncoder encoder,
            boolean markAsEnabled
    ) {
        return User
                .builder()
                .email(getEmail())
                .password(encoder.encode(getPassword()))
                .country(getCountry())
                .federalDocument(getFederalDocument())
                .roles(new HashSet<>(Set.of(Role.ROLE_USER)))
                .firstname(getFirstname())
                .lastname(getLastname())
                .username(getUsername())
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(markAsEnabled)
                .build();
    }
}
