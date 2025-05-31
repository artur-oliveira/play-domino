package org.playdomino.services.game.process.cancel.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.exceptions.game.DominoGameVoteException;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.services.game.process.cancel.BeforeCancelGameService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Service that validates if a game cancellation vote can be processed.
 * Prevents single player from disapproving game cancellation.
 */
@Service
@Validated
@RequiredArgsConstructor
public class UserHostCancelVoteValidator implements BeforeCancelGameService {

    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public void process(final CancelDominoGame cancelGame) {
        if (isUserHostDisapproval(cancelGame)) {
            throwDisapprovalNotAllowed();
        }
    }

    private boolean isUserHostDisapproval(CancelDominoGame cancelGame) {
        return Objects.equals(cancelGame.getGame().getHost(), UserUtils.currentUser())
                && !cancelGame.getApprove();
    }

    private void throwDisapprovalNotAllowed() {
        throw new DominoGameVoteException(
                DominoGameExceptionConstants.CANNOT_DISAPPROVE,
                messagesComponent.getMessage(DominoGameExceptionConstants.CANNOT_DISAPPROVE)
        );
    }
}