package org.playdomino.services.game;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DominoGamePlayerServiceImpl implements DominoGamePlayerService {
    private static final String NOT_A_PLAYER_CODE = DominoGameExceptionConstants.NOT_A_PLAYER;

    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public DominoGamePlayer getPlayerInGame(final DominoGame game) {
        Optional<DominoGamePlayer> gamePlayer = dominoGamePlayerRepository
                .findDominoGamePlayerByGameAndUser(game, UserUtils.currentUser());

        return gamePlayer.orElseThrow(() ->
                new DominoGameException(NOT_A_PLAYER_CODE,
                        messagesComponent.getMessage(NOT_A_PLAYER_CODE)));
    }

}