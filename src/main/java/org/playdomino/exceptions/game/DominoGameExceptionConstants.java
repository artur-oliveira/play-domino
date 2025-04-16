package org.playdomino.exceptions.game;

public final class DominoGameExceptionConstants {
    private DominoGameExceptionConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String LAST_GAME_NOT_FINISHED = "dominogame.not-finished";
    public static final String NOT_AVAILABLE_FOR_BET = "dominogame.not-available-for-bet";
    public static final String USER_ALREADY_JOINED_GAME = "dominogame.user-already-joined-game";
    public static final String INVALID_PASSWORD = "dominogame.invalid-password";
}
