package org.playdomino.exceptions.game;

public final class DominoGameExceptionConstants {
    private DominoGameExceptionConstants() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String GAME_NOT_FOUND = "dominogame.not-found";
    public static final String NOT_HAVE_INFO_FOR_BET = "dominogame.not-have-info-for-bet";
    public static final String NOT_AVAILABLE_FOR_BET = "dominogame.not-available-for-bet";
    public static final String NOT_A_PLAYER = "dominogame.not-a-player";

    public static final String LAST_GAME_NOT_FINISHED = "dominogame.add-player.not-finished";
    public static final String USER_ALREADY_JOINED_GAME = "dominogame.add-player.user-already-joined-game";
    public static final String NOT_WAITING_FOR_PLAYERS = "dominogame.add-player.not-waiting-for-players";
    public static final String GAME_IS_FULL = "dominogame.add-player.game-is-full";
    public static final String INVALID_PASSWORD = "dominogame.add-player.invalid-password";

    public static final String INVALID_STATUS_FOR_EXIT = "dominogame.exit.invalid-status";
    public static final String INVALID_STATUS_FOR_REMOVE = "dominogame.remove.invalid-status";
    public static final String CANNOT_REMOVE = "dominogame.remove.cannot-remove";

    public static final String ALREADY_VOTED = "dominogame.vote.already-voted";
    public static final String CANNOT_DISAPPROVE = "dominogame.vote.cannot-disapprove";
}
