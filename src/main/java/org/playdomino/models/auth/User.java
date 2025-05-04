package org.playdomino.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.playdomino.components.utils.RandomUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Column(name = "username", nullable = false)
    private String username;

    @NotEmpty
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotEmpty
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "federal_document", unique = true)
    private String federalDocument;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Country country;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_user_roles_user"))
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "last_modified_at", nullable = false)
    private ZonedDateTime lastModifiedAt;

    @Override
    @Transient
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(getRoles()).orElseGet(HashSet::new).stream().map(it -> new SimpleGrantedAuthority(it.name())).toList();
    }

    @Transient
    @JsonProperty("display_name")
    public String getDisplayName() {
        if (Objects.isNull(getFirstname()) && Objects.isNull(getLastname())) {
            return getUsername();
        }
        return Stream.of(getFirstname(), getLastname()).filter(Objects::nonNull).map(String::trim).filter(it -> !it.isBlank()).collect(Collectors.joining(" "));
    }

    @JsonIgnore
    public UserVerification createUserVerification() {
        return UserVerification
                .builder()
                .user(this)
                .token(RandomUtils.getAlphaUpperNumericRandomString(6))
                .build();
    }

    @Transient
    @JsonIgnore
    public boolean cannotBetOrPerformTransactions() {
        return Objects.isNull(getFederalDocument());
    }

    @Transient
    @JsonIgnore
    public boolean canUpdateFederalDocument() {
        return Objects.isNull(getFederalDocument());
    }
}

