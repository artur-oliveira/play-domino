package org.playdomino.services.game.process.move.after;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameMove;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.repositories.game.DominoGameMoveRepository;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.services.game.process.move.AfterCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 150)
@RequiredArgsConstructor
public class UpdateCurrentPlayerService implements AfterCreateMoveService {

    private final DominoGameRepository dominoGameRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(DominoGame game, CreateGameMove createGameMove) {
        game.nextPlayer();
        dominoGameRepository.save(game);
    }
}
