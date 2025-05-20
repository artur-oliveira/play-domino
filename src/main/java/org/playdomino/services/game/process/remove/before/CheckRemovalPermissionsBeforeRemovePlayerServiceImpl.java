package org.playdomino.services.game.process.remove.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.dto.RemovePlayerDominoGame;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.services.game.process.remove.BeforeRemovePlayerService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckRemovalPermissionsBeforeRemovePlayerServiceImpl implements BeforeRemovePlayerService {

    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    boolean currentUserIsTheHost(RemovePlayerDominoGame removePlayerDominoGame) {
        return Objects.equals(removePlayerDominoGame.getDominoGame().getHost().getId(), UserUtils.currentUser().getId());
    }

    boolean currentUserIsThePlayerToRemove(RemovePlayerDominoGame removePlayerDominoGame) {
        return Objects.equals(removePlayerDominoGame.getDominoGamePlayer().getUser().getId(), UserUtils.currentUser().getId());
    }

    @Override
    public void process(final RemovePlayerDominoGame removePlayerDominoGame) {
        if (currentUserIsTheHost(removePlayerDominoGame) || currentUserIsThePlayerToRemove(removePlayerDominoGame)) {
            return;
        }
        throw new DominoGameException(DominoGameExceptionConstants.CANNOT_REMOVE, messagesComponent.getMessage(DominoGameExceptionConstants.CANNOT_REMOVE));
    }
}
