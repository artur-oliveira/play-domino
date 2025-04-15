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
public class UserDoesNotExistsByFederalDocumentAndCountryCreateUserValidation implements CreateUserValidation {
    private final UserRepository userRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void validate(User user) {
        if (userRepository.findUserByFederalDocumentAndCountry(user.getFederalDocument(), user.getCountry()).isPresent()) {
            throw new UserAlreadyExistsException(messagesComponent.getMessage(AuthExceptionConstants.USER_EXISTS), user.getFederalDocument());
        }
    }
}
