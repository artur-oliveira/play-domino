package org.playdomino.services.auth;

public interface UserVerificationService {
    void verifyUser(String verificationToken);
}
