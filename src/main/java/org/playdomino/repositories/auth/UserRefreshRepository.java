package org.playdomino.repositories.auth;

import org.playdomino.models.auth.UserRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRefreshRepository extends JpaRepository<UserRefresh, Long> {
    @Transactional(readOnly = true)
    @Query(value = "select r from UserRefresh r where r.token = :token")
    Optional<UserRefresh> findByToken(@Param("token") String token);
}
