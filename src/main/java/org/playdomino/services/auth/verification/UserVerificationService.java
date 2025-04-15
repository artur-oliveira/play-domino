package org.playdomino.services.auth.verification;

public interface UserVerificationService {
    void verifyUser(String verificationToken);
}
