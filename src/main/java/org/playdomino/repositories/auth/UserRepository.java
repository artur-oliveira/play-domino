package org.playdomino.repositories.auth;

import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findUserByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findUserByUsername(String userName);

    @Transactional(readOnly = true)
    Optional<User> findUserByFederalDocumentAndCountry(String federalDocument, Country country);
}
