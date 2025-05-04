package org.playdomino.services.auth.user;

import lombok.Getter;
import org.playdomino.models.auth.User;
import org.playdomino.repositories.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Getter
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    private UsernameNotFoundException notFoundException(String username) {
        return new UsernameNotFoundException(username + " not found");
    }

    @Transactional(readOnly = true)
    public User loadUser(String emailOrUserName) {
        Optional<User> userOptional = Optional.empty();
        if (Objects.nonNull(emailOrUserName) && emailOrUserName.contains("@")) {
            userOptional = getUserRepository().findUserByEmail(emailOrUserName);
        } else if (Objects.nonNull(emailOrUserName)) {
            userOptional = getUserRepository().findUserByUsername(emailOrUserName);
        }
        return userOptional.orElseThrow(() -> notFoundException(emailOrUserName));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUser(username);
    }
}
