package org.playdomino.repositories.auth;

import org.playdomino.models.auth.Country;
import org.playdomino.models.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    @Query(value = "select u from User u JOIN FETCH u.roles where u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Transactional(readOnly = true)
    @Query(value = "select u from User u JOIN FETCH u.roles where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Transactional(readOnly = true)
    @Query(value = "select u from User u JOIN FETCH u.roles where u.federalDocument = :federal_document and u.country = :country")
    Optional<User> findUserByFederalDocumentAndCountry(@Param("federal_document") String federalDocument, @Param("country") Country country);
}
