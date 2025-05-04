package org.playdomino.services.auth.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.models.auth.dto.UserUpdate;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.services.auth.user.validation.create.CreateUserValidation;
import org.playdomino.services.auth.user.validation.update.UpdateUserValidation;
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
    private final List<UpdateUserValidation> updateUserValidations;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(final UserUpdate user) {
        user.setCurrentUser(UserUtils.currentUser());
        updateUserValidations.forEach(validation -> validation.validate(user));
        userRepository.save(user.updatedUser());
    }
}
