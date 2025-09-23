package org.playdomino.models.game.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.playdomino.models.game.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateGameMoveTest {

    @Test
    @DisplayName("Test CreateGameMove: Should close the game")
    void testCreateGameMoveThatClosesTheGame() {
        DominoGame game = DominoGame
                .builder()
                .rounds(List.of(DominoGameRound
                        .builder()
                        .nextLeftTileNumber(5)
                        .nextRightTileNumber(6)
                        .build()))
                .players(List.of(
                        DominoGamePlayer
                                .builder()
                                .hand(List.of(DominoTile.ONE_FOUR))

                                .build(),
                        DominoGamePlayer
                                .builder()
                                .hand(List.of(DominoTile.ONE_FIVE, DominoTile.FIVE_SIX))
                                .build()
                ))
                .build();

        assertTrue(CreateGameMove.builder().tile(DominoTile.FIVE_SIX).moveDirection(MoveDirection.LEFT).build().wouldCloseTheGame(game));
    }

    @Test
    @DisplayName("Test CreateGameMove: Should close the game")
    void testCreateGameMoveThatDoesNotClosesTheGame() {
        DominoGame game = DominoGame
                .builder()
                .rounds(List.of(DominoGameRound
                        .builder()
                        .nextLeftTileNumber(5)
                        .nextRightTileNumber(6)
                        .build()))
                .players(List.of(
                        DominoGamePlayer
                                .builder()
                                .hand(List.of(DominoTile.ONE_FOUR))

                                .build(),
                        DominoGamePlayer
                                .builder()
                                .hand(List.of(DominoTile.ONE_FIVE, DominoTile.FIVE_SIX))
                                .build()
                ))
                .build();

        assertTrue(CreateGameMove.builder().tile(DominoTile.FIVE_SIX).moveDirection(MoveDirection.RIGHT).build().wouldCloseTheGame(game));
    }

}