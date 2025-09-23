package org.playdomino.services.game.distribution;

import lombok.extern.log4j.Log4j2;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoTile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@Log4j2
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
                .map(it -> {
                    int index = dominoGame.getPlayers().indexOf(it);
                    log.info("found player with same value tile on the game {} at index {}", dominoGame.getId(), index);
                    return index;
                })
                .orElseGet(() -> next.findFirstGamePlayer(dominoGame));
    }
}
