package org.playdomino.services.game.move;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.services.game.DominoGameService;
import org.playdomino.services.game.process.move.AfterCreateMoveService;
import org.playdomino.services.game.process.move.BeforeCreateMoveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DominoGameMoveServiceImpl implements DominoGameMoveService {

    private final DominoGameService dominoGameService;
    private final List<BeforeCreateMoveService> beforeCreateMoveServices;
    private final List<AfterCreateMoveService> afterCreateMoveServices;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame addMove(CreateGameMove createGameMove) {
        DominoGame game = dominoGameService.findDominoGameById(createGameMove.getGameId());
        beforeCreateMoveServices.forEach(it -> it.process(game, createGameMove));
        afterCreateMoveServices.forEach(it -> it.process(game, createGameMove));

        return game;
    }
}
