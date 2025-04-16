package org.playdomino.services.game.validation.cancel;

import org.playdomino.models.game.DominoGame;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckCancelVoteBeforeCancelGameService implements BeforeCancelGameService {
    @Transactional(readOnly = true)
    public void process(final DominoGame game) {

    }
}
