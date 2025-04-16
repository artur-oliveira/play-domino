package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoMove;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.models.game.dto.JoinDominoGame;
import org.playdomino.services.game.DominoGameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/domino-game")
@RequiredArgsConstructor
public class DominoGameController {
    private final DominoGameService dominoGameService;

    @PostMapping
    public DominoGameDTO createGame(@Valid @RequestBody CreateDominoGame createDominoGame) {
        return DominoGameDTO.of(dominoGameService.create(createDominoGame));
    }

    @PostMapping("/{id}/join")
    public DominoGameDTO joinGame(
            @PathVariable Long id,
            @Valid @RequestBody JoinDominoGame joinGame
    ) {
        joinGame.setGameId(id);
        return DominoGameDTO.of(dominoGameService.join(joinGame));
    }
}
