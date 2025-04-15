package org.playdomino.services.email;

import org.playdomino.models.auth.User;

public interface EmailService {
    void sendNewUserConfirmationEmail(User user);
}
