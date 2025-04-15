package org.playdomino.services.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.auth.AuthExceptionConstants;
import org.playdomino.exceptions.auth.UserAlreadyExistsException;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.services.auth.validation.CreateUserValidation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Getter
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<CreateUserValidation> createUserValidations;


    @Override
    @Transactional
    public User create(final UserCreate createUser) {
        User user = createUser.asUser(getPasswordEncoder());
        createUserValidations.forEach(validation -> validation.validate(user));
        return userRepository.saveAndFlush(user);
    }
}
