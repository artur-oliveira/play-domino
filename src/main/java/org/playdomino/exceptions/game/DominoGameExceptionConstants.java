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

    public static final String PLAYERS_SIZE_FOR_DISTRIBUTE_TILES = "dominogame.tile.distribute-quantity";
    public static final String NOT_ABLE_TO_FIND_FIRST_GAME_PLAYER = "dominogame.not-able-to-find-first-player";

    public static final String NOT_CURRENT_TURN = "dominogame.move.not-current-turn";
    public static final String MOVE_INVALID_TILE = "dominogame.move.invalid-tile";
    public static final String MOVE_INVALID_DIRECTION = "dominogame.move.invalid-direction";
    public static final String MOVE_WOULD_CLOSE_THE_GAME = "dominogame.move.would-close-the-game";

}
