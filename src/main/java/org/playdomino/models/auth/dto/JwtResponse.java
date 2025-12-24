package org.playdomino.models.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
