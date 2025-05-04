package org.playdomino.services.auth.user.validation.create;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserAlreadyExistsException;
import org.playdomino.models.auth.User;
import org.playdomino.repositories.auth.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniqueUsernameValidation implements CreateUserValidation {
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void validate(User user) {
        validateUsernameUniqueness(user);
    }

    @Transactional(readOnly = true)
    void validateUsernameUniqueness(User user) {
        userRepository.findUserByUsername(user.getUsername())
                .ifPresent(existingUser -> {
                    String errorMessage = getValidationErrorMessage();
                    throw new UserAlreadyExistsException(errorMessage, user.getUsername());
                });
    }

    private String getValidationErrorMessage() {
        return messagesComponent.getMessage(AuthExceptionConstants.USER_ALREADY_EXISTS);
    }
}