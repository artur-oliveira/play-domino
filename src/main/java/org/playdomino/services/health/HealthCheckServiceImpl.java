package org.playdomino.services.health;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthCheckServiceImpl implements HealthCheckService {
    private final EntityManager entityManager;

    @Override
    public void checkDatabaseConnection() {
        entityManager.createNativeQuery("SELECT 1", Void.class);
    }
}
