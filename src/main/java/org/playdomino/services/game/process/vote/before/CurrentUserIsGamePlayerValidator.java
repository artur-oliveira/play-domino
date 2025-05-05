package org.playdomino.services.game.process.vote.before;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.services.game.DominoGamePlayerService;
import org.playdomino.services.game.process.vote.BeforeGameVoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentUserIsGamePlayerValidator implements BeforeGameVoteService {
    private final DominoGamePlayerService dominoGamePlayerService;

    @Transactional(readOnly = true)
    public void process(DominoGame game) {
        dominoGamePlayerService.getPlayerInGame(game);
    }
}
