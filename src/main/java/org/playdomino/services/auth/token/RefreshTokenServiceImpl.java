package org.playdomino.services.auth.token;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.UserRefresh;
import org.playdomino.repositories.auth.UserRefreshRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRefreshRepository userRefreshRepository;

    @Override
    @Transactional
    public String issueToken(Authentication authentication) {
        byte[] randomBytes = new byte[128];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        String refresh = Base64
                .getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

        userRefreshRepository.save(UserRefresh
                .builder()
                .user((User) authentication.getPrincipal())
                .revoked(false)
                .token(refresh)
                .expiresAt(ZonedDateTime.now().plusDays(30L).with(LocalTime.MAX))
                .build());

        return refresh;
    }

    public boolean isValid(String token) {
        return userRefreshRepository.findByToken(token).map(UserRefresh::isValid).orElse(false);
    }
}
