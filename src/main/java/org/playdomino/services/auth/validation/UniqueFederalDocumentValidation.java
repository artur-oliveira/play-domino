package org.playdomino.services.auth.validation;

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
public class UniqueFederalDocumentValidation implements CreateUserValidation {
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void validate(User user) {
        validateFederalDocumentUniqueNess(user);
    }

    @Transactional(readOnly = true)
    void validateFederalDocumentUniqueNess(User user) {
        userRepository.findUserByFederalDocumentAndCountry(user.getFederalDocument(), user.getCountry())
                .ifPresent(existingUser -> {
                    String errorMessage = getValidationErrorMessage();
                    throw new UserAlreadyExistsException(errorMessage, user.getFederalDocument());
                });
    }

    private String getValidationErrorMessage() {
        return messagesComponent.getMessage(AuthExceptionConstants.USER_ALREADY_EXISTS);
    }
}