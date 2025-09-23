package org.playdomino.services.game.distribution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGamePlayer;
import org.playdomino.models.game.DominoTile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindFirstGamePlayerTest {

    @Autowired
    @Qualifier("findFirstGamePlayerWithSixSix")
    FindFirstGamePlayer findFirstGamePlayer;

    @Test
    @DisplayName("Test FindFirstGamePlayer: Should get the first game player when it does have the six six tile")
    void testFindFirstGamePlayerWithSixSix() {
        DominoGame dominoGame = DominoGame
                .builder()
                .players(new ArrayList<>(List.of(
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_ONE, DominoTile.ONE_SIX)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.THREE_THREE, DominoTile.THREE_FIVE)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_ONE, DominoTile.SIX_SIX)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_TWO, DominoTile.TWO_SIX)).build()
                )))
                .build();
        assertEquals(2, findFirstGamePlayer.findFirstGamePlayer(dominoGame));
    }

    @Test
    @DisplayName("Test FindFirstGamePlayer: Should get the first game player when it does have the higher same value tile")
    void testFindFirstGamePlayerWithSameValue() {
        DominoGame dominoGame = DominoGame
                .builder()
                .players(new ArrayList<>(List.of(
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_ONE, DominoTile.ONE_SIX)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.THREE_THREE, DominoTile.THREE_FIVE)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_ONE, DominoTile.FOUR_FOUR)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_TWO, DominoTile.FIVE_FIVE)).build()
                )))
                .build();
        assertEquals(3, findFirstGamePlayer.findFirstGamePlayer(dominoGame));
    }


    @Test
    @DisplayName("Test FindFirstGamePlayer: Should get the first game player when it does have the higher tile")
    void testFindFirstGamePlayerWithHigherValue() {
        DominoGame dominoGame = DominoGame
                .builder()
                .players(new ArrayList<>(List.of(
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_THREE, DominoTile.ONE_SIX)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.TWO_FIVE, DominoTile.FIVE_SIX)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.THREE_FIVE, DominoTile.FOUR_FIVE)).build(),
                        DominoGamePlayer.builder().hand(List.of(DominoTile.ONE_TWO, DominoTile.ONE_FIVE)).build()
                )))
                .build();
        assertEquals(1, findFirstGamePlayer.findFirstGamePlayer(dominoGame));
    }


}