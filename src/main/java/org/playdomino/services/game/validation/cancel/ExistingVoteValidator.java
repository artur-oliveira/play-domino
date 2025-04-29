package org.playdomino.services.game.validation.cancel;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.exceptions.game.DominoGameVoteException;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.VoteType;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.repositories.game.DominoGameVoteRepository;
import org.playdomino.services.game.DominoGamePlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExistingVoteValidator implements BeforeCancelGameService {
    private static final VoteType VOTE_TYPE = VoteType.CANCEL_GAME;

    private final DominoGameVoteRepository dominoGameVoteRepository;
    private final DominoGamePlayerService dominoGamePlayerService;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void process(CancelDominoGame cancelGame) {
        DominoGamePlayer player = dominoGamePlayerService.getPlayerInGame(cancelGame.getGame());
        checkExistingVote(player, cancelGame);
    }

    @Transactional(readOnly = true)
    public void checkExistingVote(DominoGamePlayer player, CancelDominoGame cancelGame) {
        dominoGameVoteRepository
                .findDominoGameVoteByGameAndPlayerAndVoteType(cancelGame.getGame(), player, VOTE_TYPE)
                .ifPresent(vote -> {
                    String message = messagesComponent.getMessage(DominoGameExceptionConstants.ALREADY_VOTED);
                    throw new DominoGameVoteException(
                            DominoGameExceptionConstants.ALREADY_VOTED,
                            String.format("Player %s has already voted to cancel the game. %s",
                                    player.getUser().getUsername(),
                                    message)
                    );
                });
    }
}