package org.playdomino.models.auth.dto;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserCreate {
    @NotNull
    private Country country;
    @NotEmpty
    private String federalDocument;
    @Email
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,128}$",
            message = "{validation.user.password.pattern}"
    )
    private String password;
}
