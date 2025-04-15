package org.playdomino.exceptions.auth;

public final class AuthExceptionConstants {
    private AuthExceptionConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String USER_ALREADY_EXISTS = "user.already-exists";
    public static final String USER_DOES_NOT_EXISTS = "user.does-not-exists";
    public static final String USER_INVALID_REFRESH_TOKEN = "user.invalid-refresh-token";
    public static final String USER_INVALID_AUTHENTICATION = "user.invalid-authentication";
}
