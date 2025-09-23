package org.playdomino.services.game.distribution;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoTile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class FindFirstGamePlayerWithSameValue implements FindFirstGamePlayer {

    private final FindFirstGamePlayer next;

    public FindFirstGamePlayerWithSameValue(
            @Qualifier("findFirstGamePlayerWithHigherValue") FindFirstGamePlayer next
    ) {
        this.next = next;
    }

    @Override
    public int findFirstGamePlayer(DominoGame dominoGame) {
        return dominoGame
                .getPlayers()
                .stream()
                .filter(it -> it.getHand().stream().anyMatch(DominoTile::isSameValue))
                .max(Comparator.comparingInt(it -> it.getHand().stream().filter(DominoTile::isSameValue).max(Comparator.comparing(DominoTile::getSum)).map(DominoTile::getSum).orElse(Integer.MIN_VALUE)))
                .map(it -> dominoGame.getPlayers().indexOf(it))
                .orElseGet(() -> next.findFirstGamePlayer(dominoGame));
    }
}
