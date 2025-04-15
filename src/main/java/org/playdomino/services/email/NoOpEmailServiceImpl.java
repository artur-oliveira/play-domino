package org.playdomino.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.UserVerification;
import org.playdomino.repositories.auth.UserVerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoOpEmailServiceImpl implements EmailService {
    private final UserVerificationRepository userVerificationRepository;

    @Override
    @Transactional
    public void sendNewUserConfirmationEmail(User user) {
        final UserVerification userVerification = userVerificationRepository.saveAndFlush(user.createUserVerification());
        log.info("token for user {} is {}", user.getId(), userVerification.getToken());
    }
}
