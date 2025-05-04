package org.playdomino;

import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.Role;
import org.playdomino.models.auth.User;
import org.playdomino.models.auth.dto.UserCreate;
import org.playdomino.repositories.auth.UserRepository;
import org.playdomino.repositories.auth.UserVerificationRepository;
import org.playdomino.services.auth.user.UserService;
import org.playdomino.services.auth.verification.UserVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class TestConfigurationData {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserVerificationRepository userVerificationRepository;
    @Autowired
    private UserVerificationService userVerificationService;

    @Transactional
    void createUser() {
        User user = userService.create(UserCreate
                .builder()
                .username("testuser")
                .email("testuser@test.com")
                .password("Test123456@")
                .country(Country.BRAZIL)
                .federalDocument("00000000000")
                .firstname("Test")
                .lastname("User")
                .build());
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Transactional
    void createAdminUser() {
        User user = userService.create(UserCreate
                .builder()
                .username("testadmin")
                .email("testadmin@test.com")
                .password("Test123456@")
                .country(Country.BRAZIL)
                .federalDocument("11111111111")
                .firstname("Test")
                .lastname("Admin")
                .build());
        user.setEnabled(true);
        user.setRoles(new HashSet<>(Set.of(Role.ROLE_ADMIN)));
        userRepository.save(user);
    }

    @Transactional
    void verifyAll() {
        userVerificationRepository.findAll().forEach(it -> userVerificationService.verifyUser(it.getToken()));
    }

    @Transactional
    void initializeDatabase() {
        createUser();
        createAdminUser();
        verifyAll();
    }
}
