package org.playdomino.services.game;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;
import org.playdomino.repositories.game.DominoGamePlayerRepository;
import org.playdomino.repositories.game.DominoGameRoundRepository;
import org.playdomino.repositories.game.DominoGameVoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DominoGameDTOServiceImpl implements DominoGameDTOService {

    private final DominoGamePlayerRepository dominoGamePlayerRepository;
    private final DominoGameRoundRepository dominoGameRoundRepository;
    private final DominoGameVoteRepository dominoGameVoteRepository;

    @Transactional(readOnly = true)
    public DominoGameDTO getDominoGameDTO(DominoGame game) {
        return DominoGameDTO.of(
                game,
                dominoGamePlayerRepository.findAllByGameOrderById(game),
                dominoGameRoundRepository.findAllByGameOrderById(game),
                dominoGameVoteRepository.findAllByGameOrderById(game)
        );
    }
}
