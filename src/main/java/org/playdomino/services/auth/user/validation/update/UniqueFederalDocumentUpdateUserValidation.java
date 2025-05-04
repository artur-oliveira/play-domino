package org.playdomino.services.auth.user.validation.update;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserAlreadyExistsException;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserUpdate;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.services.auth.user.validation.create.CreateUserValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UniqueFederalDocumentUpdateUserValidation implements UpdateUserValidation {
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void validate(UserUpdate userUpdate) {
        validateFederalDocumentUniqueNess(userUpdate);
    }

    @Transactional(readOnly = true)
    void validateFederalDocumentUniqueNess(final UserUpdate user) {
        if (Objects.isNull(user.getFederalDocument()) || Objects.nonNull(user.getCurrentUser().getFederalDocument())) {
            return;
        }
        userRepository.findUserByFederalDocumentAndCountry(user.getFederalDocument(), user.getCurrentUser().getCountry())
                .ifPresent((existingUser) -> {
                    if (Objects.equals(existingUser.getId(), user.getCurrentUser().getId())) {
                        return;
                    }
                    String errorMessage = getValidationErrorMessage();
                    throw new UserAlreadyExistsException(errorMessage, user.getFederalDocument());
                });
    }

    private String getValidationErrorMessage() {
        return messagesComponent.getMessage(AuthExceptionConstants.USER_ALREADY_EXISTS);
    }
}