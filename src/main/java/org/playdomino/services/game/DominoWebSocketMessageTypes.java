package org.playdomino.services.game;

public final class DominoWebSocketMessageTypes {
    private DominoWebSocketMessageTypes() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final String CANCEL_GAME = "domino.game.cancel";
    public static final String USER_JOINED = "domino.game.user-joined";
    public static final String USER_LEFT = "domino.game.user-left";
    public static final String VOTE_CANCEL = "domino.game.vote.cancel";
    public static final String GAME_STARTED ="domino.game.started";
    public static final String GAME_MOVE = "domino.game.move";

}
