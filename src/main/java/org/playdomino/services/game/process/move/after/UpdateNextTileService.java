package org.playdomino.services.game.process.move.after;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameRound;
import org.playdomino.models.game.MoveDirection;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.repositories.game.DominoGameMoveRepository;
import org.playdomino.services.game.process.move.AfterCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 250)
@RequiredArgsConstructor
public class UpdateNextTileService implements AfterCreateMoveService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(DominoGame game, CreateGameMove createGameMove) {
        DominoGameRound round = game.getCurrentRound();

        if (Objects.isNull(round.getLastLeftTile()) && Objects.isNull(round.getLastRightTile())) {
            round.setLastLeftTile(createGameMove.getTile());
            round.setLastRightTile(createGameMove.getTile());
            round.setNextLeftTileNumber(createGameMove.getTile().getLeft());
            round.setNextRightTileNumber(createGameMove.getTile().getRight());
        } else if (createGameMove.getMoveDirection() == MoveDirection.RIGHT) {
            int nextBeforeSet = round.getNextRightTileNumber();
            round.setLastRightTile(createGameMove.getTile());
            round.setNextRightTileNumber(createGameMove.getTile().otherFace(nextBeforeSet));
        } else if (createGameMove.getMoveDirection() == MoveDirection.LEFT) {
            int nextBeforeSet = round.getNextLeftTileNumber();
            round.setLastLeftTile(createGameMove.getTile());
            round.setNextLeftTileNumber(createGameMove.getTile().otherFace(nextBeforeSet));
        } else {
            throw new IllegalStateException("Invalid move direction");
        }

    }
}
