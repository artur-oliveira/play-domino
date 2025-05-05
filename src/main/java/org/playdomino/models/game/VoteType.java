package org.playdomino.models.game;

import java.util.List;
import java.util.Objects;

public enum VoteType {
    CANCEL_GAME {
        @Override
        public List<Long> getNeededVotesPlayersIds(DominoGame game) {
            return game.getPlayers().stream().map(DominoGamePlayer::getId).toList();
        }

        @Override
        public String propertyName() {
            return "domino.game.vote.cancel-game";
        }
    };

    public boolean isApproved(DominoGame game) {
        return game.getVotes()
                .stream()
                .filter(it -> Objects.equals(it.getVoteType(), this) && it.isApproved())
                .count() == getNeededVotesPlayersIds(game).size();
    }

    abstract List<Long> getNeededVotesPlayersIds(DominoGame game);

    public abstract String propertyName();
}
