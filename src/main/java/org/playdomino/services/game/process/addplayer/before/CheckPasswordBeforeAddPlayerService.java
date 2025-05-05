package org.playdomino.services.game.process.addplayer.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameAddPlayerException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.services.game.process.addplayer.BeforeAddPlayerService;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Order(0)
@RequiredArgsConstructor
public class CheckPasswordBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void process(AddPlayerDominoGame playerToGame) {
        if (Objects.nonNull(playerToGame.getGame().getPassword()) && !passwordEncoder.matches(
                playerToGame.getPassword(),
                playerToGame.getGame().getPassword()
        )) {
            throw new DominoGameAddPlayerException(DominoGameExceptionConstants.INVALID_PASSWORD, messagesComponent.getMessage(DominoGameExceptionConstants.INVALID_PASSWORD));
        }
    }
}
