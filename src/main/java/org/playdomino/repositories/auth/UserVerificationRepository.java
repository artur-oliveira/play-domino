package org.playdomino.repositories.auth;

import org.playdomino.models.auth.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {
    @Transactional(readOnly = true)
    Optional<UserVerification> findUserVerificationByToken(String token);
}
