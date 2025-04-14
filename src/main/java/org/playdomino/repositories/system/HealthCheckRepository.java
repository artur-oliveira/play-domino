package org.playdomino.repositories.system;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository {
    @Query(value = "SELECT 1", nativeQuery = true)
    Long checkDatabaseConnection();
}
