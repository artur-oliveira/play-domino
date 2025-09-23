package org.playdomino.services.game.distribution;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameRound;
import org.playdomino.models.game.DominoTile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FindFirstGamePlayerWithSixSix implements FindFirstGamePlayer {

    private final FindFirstGamePlayer next;

    public FindFirstGamePlayerWithSixSix(
            @Qualifier("findFirstGamePlayerWithSameValue") FindFirstGamePlayer next
    ) {
        this.next = next;
    }

    @Override
    public int findFirstGamePlayer(DominoGame dominoGame) {
        return dominoGame
                .getPlayers()
                .stream()
                .filter(it -> it.getHand().contains(DominoTile.SIX_SIX)).findFirst()
                .map(it -> dominoGame.getPlayers().indexOf(it))
                .orElseGet(() -> next.findFirstGamePlayer(dominoGame));
    }
}
