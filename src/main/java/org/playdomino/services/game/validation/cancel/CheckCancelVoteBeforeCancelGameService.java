package org.playdomino.services.game.validation.cancel;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.exceptions.game.DominoGameVoteException;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.VoteType;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.repositories.game.DominoGameVoteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckCancelVoteBeforeCancelGameService implements BeforeCancelGameService {

    private final DominoGameVoteRepository dominoGameVoteRepository;
    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    @Transactional(readOnly = true)
    public void process(final DominoGame game) {
        final DominoGamePlayer player = dominoGamePlayerRepository.findDominoGamePlayerByGameAndUser(game, (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).orElseThrow(() -> new DominoGameVoteException(DominoGameExceptionConstants.NOT_A_PLAYER, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_A_PLAYER)));
        dominoGameVoteRepository
                .findDominoGameVoteByGameAndPlayerAndVoteType(game, player, VoteType.CANCEL_GAME)
                .ifPresent((vote) -> {
                    throw new DominoGameVoteException(DominoGameExceptionConstants.ALREADY_VOTED, messagesComponent.getMessage(DominoGameExceptionConstants.ALREADY_VOTED));
                });
    }
}
