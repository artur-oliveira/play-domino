package org.playdomino.models.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.models.game.VoteType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class DominoGameVoteDTO {
    private Long id;

    private DominoGamePlayerDTO player;

    private VoteType voteType;

    private boolean approved;

    public static DominoGameVoteDTO of(final DominoGameVote vote) {
        return DominoGameVoteDTO
                .builder()
                .id(vote.getId())
                .player(DominoGamePlayerDTO.of(vote.getPlayer(), null))
                .voteType(vote.getVoteType())
                .approved(vote.isApproved())
                .build();
    }
}
