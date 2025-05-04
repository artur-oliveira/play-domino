package org.playdomino.models.auth.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.User;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class CurrentUserDTO {
    private Long id;
    private String email;
    private String username;
    private String name;
    private String firstname;
    private String lastname;
    private String federalDocument;
    private Country country;
    private String countryCurrency;
    private String countryLocale;
    private ZonedDateTime createdAt;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    public static CurrentUserDTO of(final User user) {
        return CurrentUserDTO
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getDisplayName())
                .username(user.getUsername())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .federalDocument(user.getFederalDocument())
                .country(user.getCountry())
                .countryCurrency(user.getCountry().getCurrency())
                .countryLocale(user.getCountry().getLocale())
                .enabled(user.isEnabled())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
