package org.playdomino.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_document", columnNames = {"federal_document"}),
        @UniqueConstraint(name = "unique_user_email", columnNames = {"email"}),
        @UniqueConstraint(name = "unique_user_username", columnNames = {"username"}),
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotEmpty
    @Column(name = "lastname", nullable = false)
    private String lastname;

    @NotEmpty
    @Column(name = "username", nullable = false)
    private String username;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "federal_document", unique = true, nullable = false)
    private String federalDocument;

    @NotNull
    @Column(nullable = false)
    private Country country;

    @Builder.Default
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = false;

    @Builder.Default
    @Column(name = "account_non_Locked")
    private boolean accountNonLocked = false;

    @Builder.Default
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = false;

    @Builder.Default
    @Column(name = "enabled")
    private boolean enabled = false;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_roles_user"))
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(getRoles()).orElseGet(HashSet::new).stream().map(it -> new SimpleGrantedAuthority(it.name())).toList();
    }

    @Transient
    @JsonProperty("display_name")
    public String getDisplayName() {
        return String.join(" ", getFirstname(), getLastname());
    }
}

