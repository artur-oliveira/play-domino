package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.models.game.dto.JoinDominoGame;
import org.playdomino.services.game.DominoGameDTOService;
import org.playdomino.services.game.DominoGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/domino-game")
@RequiredArgsConstructor
public class DominoGameController {
    private final DominoGameService dominoGameService;
    private final DominoGameDTOService dominoGameDTOService;

    @GetMapping("/ongoing")
    public ResponseEntity<DominoGameDTO> ongoingGame() {
        Optional<DominoGame> dominoGame = dominoGameService.findCurrentDominoGame();
        return dominoGame.map(game -> ResponseEntity.ok(dominoGameDTOService.getDominoGameDTO(game))).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public DominoGameDTO createGame(@Valid @RequestBody CreateDominoGame createDominoGame) {
        return dominoGameDTOService.getDominoGameDTO(dominoGameService.create(createDominoGame));
    }

    @PostMapping("/{id}/join")
    public DominoGameDTO joinGame(
            @PathVariable Long id,
            @Valid @RequestBody JoinDominoGame joinGame
    ) {
        joinGame.setGameId(id);
        return dominoGameDTOService.getDominoGameDTO(dominoGameService.join(joinGame));
    }

    @PostMapping("/{id}/cancel")
    public DominoGameDTO cancelGame(
            @PathVariable Long id,
            @Valid @RequestBody CancelDominoGame cancelDominoGame
    ) {
        cancelDominoGame.setGameId(id);
        return dominoGameDTOService.getDominoGameDTO(dominoGameService.cancel(cancelDominoGame));
    }
}
