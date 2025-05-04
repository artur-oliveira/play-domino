package org.playdomino.models.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.playdomino.models.auth.Role;
import org.playdomino.models.auth.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class UserUpdate {
    @Pattern(
            regexp = "^[A-Za-zÀ-ÿ]+([ '-][A-Za-zÀ-ÿ]+)*$",
            message = "{validation.user.name.pattern}"
    )
    private String firstname;
    @JsonIgnore
    private boolean set$firstname;
    @Pattern(
            regexp = "^[A-Za-zÀ-ÿ]+([ '-][A-Za-zÀ-ÿ]+)*$",
            message = "{validation.user.name.pattern}"
    )
    private String lastname;
    @JsonIgnore
    private boolean set$lastname;
    @CPF
    @Size(min = 11, max = 11)
    private String federalDocument;
    @JsonIgnore
    private boolean set$federalDocument;
    @JsonIgnore
    private User currentUser;

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        this.set$firstname = true;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        this.set$lastname = true;
    }

    public void setFederalDocument(String federalDocument) {
        this.federalDocument = federalDocument;
        this.set$federalDocument = true;
    }

    @JsonIgnore
    public User updatedUser() {
        if (currentUser.canUpdateFederalDocument() && isSet$federalDocument()) {
            currentUser.setFederalDocument(getFederalDocument());
        }
        if (isSet$firstname()) {
            currentUser.setFirstname(getFirstname());
        }
        if (isSet$lastname()) {
            currentUser.setLastname(getLastname());
        }
        return currentUser;
    }
}
