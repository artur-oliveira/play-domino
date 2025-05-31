package org.playdomino.models.game;

import java.util.List;
import java.util.Objects;

public enum VoteType {
    CANCEL_GAME {
        List<Long> getNeededVotesPlayersIds(DominoGame game) {
            return game.getPlayers().stream().map(DominoGamePlayer::getId).toList();
        }

        @Override
        public boolean isApproved(DominoGame game, DominoGameVote vote) {
            if (game.getStatus() == GameStatus.WAITING_FOR_PLAYERS && vote.isVoteApproved(game.getHost())) {
                return true;
            }
            return game.getVotes()
                    .stream()
                    .filter(it -> Objects.equals(it.getVoteType(), this) && it.isApproved())
                    .count() == getNeededVotesPlayersIds(game).size();
        }

        @Override
        public String propertyName() {
            return "domino.game.vote.cancel";
        }
    };

    public abstract boolean isApproved(
            DominoGame game,
            DominoGameVote vote
    );

    public abstract String propertyName();
}
