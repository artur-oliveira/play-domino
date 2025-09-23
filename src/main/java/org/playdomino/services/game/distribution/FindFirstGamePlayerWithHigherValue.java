package org.playdomino.services.game.distribution;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoTile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class FindFirstGamePlayerWithHigherValue implements FindFirstGamePlayer {

    private final MessagesComponent messagesComponent;

    @Override
    public int findFirstGamePlayer(DominoGame dominoGame) {
        return dominoGame
                .getPlayers()
                .stream()
                .max(Comparator.comparingInt(it -> it.getHand().stream().max(Comparator.comparing(DominoTile::getSum)).map(DominoTile::getSum).orElse(Integer.MIN_VALUE)))
                .map(it -> dominoGame.getPlayers().indexOf(it))
                .orElseThrow(() -> new DominoGameException(DominoGameExceptionConstants.NOT_ABLE_TO_FIND_FIRST_GAME_PLAYER, messagesComponent));
    }
}
