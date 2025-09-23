package org.playdomino.services.game.process.move.after;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameMove;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.dto.CreateGameMove;
import org.playdomino.repositories.game.DominoGameMoveRepository;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.services.game.process.move.AfterCreateMoveService;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Order(Ordered.HIGHEST_PRECEDENCE + 200)
@RequiredArgsConstructor
public class RemoveTileFromHandService implements AfterCreateMoveService {

    private final DominoGamePlayerRepository dominoGamePlayerRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(DominoGame game, CreateGameMove createGameMove) {
        DominoGamePlayer player = game.getPlayer(UserUtils.currentUser());
        player.getHand().remove(createGameMove.getTile());
        dominoGamePlayerRepository.save(player);
    }
}
