package org.playdomino.services.auth;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserVerificationException;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.UserVerification;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.repositories.auth.UserVerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserVerificationServiceImpl implements UserVerificationService {
    private final UserVerificationRepository userVerificationRepository;
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    UserVerificationException newException(String code) {
        return new UserVerificationException(code, messagesComponent.getMessage(code));
    }

    @Override
    @Transactional
    public void verifyUser(String verificationToken) {
        final UserVerification verification = userVerificationRepository.findUserVerificationByToken(verificationToken).orElseThrow(() -> newException(AuthExceptionConstants.USER_VERIFICATION_NOT_FOUND));
        if (verification.getExpiresAt().isBefore(ZonedDateTime.now())) {
            throw newException(AuthExceptionConstants.USER_VERIFICATION_EXPIRED);
        }
        if (Objects.nonNull(verification.getVerifiedAt())) {
            throw newException(AuthExceptionConstants.USER_VERIFICATION_ALREADY_VERIFIED);
        }
        final User user = verification.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verification.setVerifiedAt(ZonedDateTime.now());
        userVerificationRepository.save(verification);
    }
}
