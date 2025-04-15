package org.playdomino.services.auth.verification;

import org.playdomino.models.auth.User;

public interface AfterVerificationService {
    void afterVerification(User user);
}
