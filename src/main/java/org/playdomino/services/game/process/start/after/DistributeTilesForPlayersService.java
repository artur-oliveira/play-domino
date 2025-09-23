package org.playdomino.services.game.process.start.after;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameRound;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.repositories.game.DominoGameRoundRepository;
import org.playdomino.services.game.DominoGamePlayerService;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 150)
@AllArgsConstructor
public class DistributeTilesForPlayersService implements AfterStartGameService {
    private final MessagesComponent messagesComponent;
    private final DominoGamePlayerRepository dominoGamePlayerService;
    private final DominoGameRoundRepository dominoGameRoundRepository;

    int calculateTileQuantityForEachPlayer(DominoGame game) {
        return switch (game.getPlayers().size()) {
            case 2, 4 -> 7;
            case 3 -> 9;
            default ->
                    throw new DominoGameException(DominoGameExceptionConstants.PLAYERS_SIZE_FOR_DISTRIBUTE_TILES, messagesComponent.getMessage(DominoGameExceptionConstants.PLAYERS_SIZE_FOR_DISTRIBUTE_TILES));
        };
    }

    @Override
    @Transactional
    public void process(DominoGame dominoGame) {
        DominoGameRound round = dominoGame.getRounds().getLast();

        final int quantityTiles = calculateTileQuantityForEachPlayer(dominoGame);
        for (int i = 0; i < quantityTiles; i++) {
            dominoGame.getPlayers().forEach(it -> {
                it.getHand().add(round.getPile().removeFirst());
            });
        }
        dominoGamePlayerService.saveAll(dominoGame.getPlayers());
        dominoGameRoundRepository.saveAll(dominoGame.getRounds());
    }
}
