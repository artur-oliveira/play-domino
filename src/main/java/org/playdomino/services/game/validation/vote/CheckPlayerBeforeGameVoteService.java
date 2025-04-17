package org.playdomino.services.game.validation.vote;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.exceptions.game.DominoGameVoteException;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckPlayerBeforeGameVoteService implements BeforeGameVoteService {
    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    @Transactional(readOnly = true)
    public void process(DominoGame game) {
        dominoGamePlayerRepository.findDominoGamePlayerByGameAndUser(game, (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).orElseThrow(() -> new DominoGameVoteException(DominoGameExceptionConstants.NOT_A_PLAYER, messagesComponent.getMessage(DominoGameExceptionConstants.NOT_A_PLAYER)));
    }
}
