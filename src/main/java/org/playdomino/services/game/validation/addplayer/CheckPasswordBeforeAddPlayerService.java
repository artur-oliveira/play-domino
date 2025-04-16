package org.playdomino.services.game.validation.addplayer;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.AddPlayerToGame;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckPasswordBeforeAddPlayerService implements BeforeAddPlayerService {

    private final MessagesComponent messagesComponent;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void process(AddPlayerToGame playerToGame) {
        if (Objects.nonNull(playerToGame.getGame().getPassword()) && !passwordEncoder.matches(
                playerToGame.getPassword(),
                playerToGame.getGame().getPassword()
        )) {
            throw new DominoGameException(DominoGameExceptionConstants.INVALID_PASSWORD, messagesComponent.getMessage(DominoGameExceptionConstants.INVALID_PASSWORD));
        }
    }
}
