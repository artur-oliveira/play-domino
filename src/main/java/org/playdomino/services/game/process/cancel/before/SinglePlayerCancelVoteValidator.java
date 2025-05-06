package org.playdomino.services.game.process.cancel.before;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.exceptions.game.DominoGameVoteException;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.services.game.process.cancel.BeforeCancelGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Service that validates if a game cancellation vote can be processed.
 * Prevents single player from disapproving game cancellation.
 */
@Service
@Validated
@RequiredArgsConstructor
public class SinglePlayerCancelVoteValidator implements BeforeCancelGameService {
    private static final int SINGLE_PLAYER_COUNT = 1;

    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void process(final CancelDominoGame cancelGame) {
        if (isSinglePlayerDisapproval(cancelGame)) {
            throwDisapprovalNotAllowed();
        }
    }

    private boolean isSinglePlayerDisapproval(CancelDominoGame cancelGame) {
        return cancelGame.getGame().getPlayers().size() == SINGLE_PLAYER_COUNT
                && !cancelGame.getApprove();
    }

    private void throwDisapprovalNotAllowed() {
        throw new DominoGameVoteException(
                DominoGameExceptionConstants.CANNOT_DISAPPROVE,
                messagesComponent.getMessage(DominoGameExceptionConstants.CANNOT_DISAPPROVE)
        );
    }
}