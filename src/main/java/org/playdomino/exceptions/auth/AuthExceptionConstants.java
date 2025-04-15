package org.playdomino.exceptions.auth;

public final class AuthExceptionConstants {
    private AuthExceptionConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String USER_EXISTS = "user.exists";
}
