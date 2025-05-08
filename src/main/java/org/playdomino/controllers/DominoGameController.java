package org.playdomino.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.playdomino.models.financial.dto.WalletTransactionDTO;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.models.game.dto.JoinDominoGame;
import org.playdomino.models.generic.ListReponse;
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

    @GetMapping("/public")
    public ListReponse<DominoGameDTO> getPublicGames(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size
    ) {
        return new ListReponse<>(
                dominoGameService.findAllPublicGames(
                        page.orElse(0),
                        size.orElse(10)
                ).stream().map(it -> DominoGameDTO.of(it, it.getPlayers())).toList()
        );
    }

    @GetMapping("/ongoing")
    public ResponseEntity<DominoGameDTO> getOngoingGame() {
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
