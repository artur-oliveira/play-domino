package org.playdomino.services.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.services.auth.validation.CreateUserValidation;
import org.playdomino.services.email.EmailService;
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
    private final List<EmailService> emailServices;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User create(final UserCreate createUser) {
        final User user = createUser.asUser(getPasswordEncoder(), emailServices.isEmpty());
        createUserValidations.forEach(validation -> validation.validate(user));
        final User dbUser = userRepository.saveAndFlush(user);
        emailServices.forEach(it -> it.sendNewUserConfirmationEmail(dbUser));
        return dbUser;
    }
}
