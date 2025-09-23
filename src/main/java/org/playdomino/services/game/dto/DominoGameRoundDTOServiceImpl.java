package org.playdomino.services.game.dto;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameRound;
import org.playdomino.models.game.dto.DominoGameRoundDTO;
import org.playdomino.repositories.game.DominoGameMoveRepository;
import org.playdomino.repositories.game.DominoGameRoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DominoGameRoundDTOServiceImpl implements DominoGameRoundDTOService {

    private final DominoGameRoundRepository dominoGameRoundRepository;
    private final DominoGameMoveRepository dominoGameMoveRepository;

    @Transactional(readOnly = true)
    public List<DominoGameRoundDTO> getDominoGameRoundDTOs(DominoGame game) {
        List<DominoGameRound> rounds = dominoGameRoundRepository.findAllByGameOrderById(game);

        return rounds.stream().map(it -> DominoGameRoundDTO.of(
                it,
                dominoGameMoveRepository.findGameMovesByGameRoundOrderById(it),
                it.getPile()
        )).toList();
    }

}
