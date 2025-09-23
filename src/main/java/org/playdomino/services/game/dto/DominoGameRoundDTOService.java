package org.playdomino.services.game.dto;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.DominoGameRoundDTO;

import java.util.List;

public interface DominoGameRoundDTOService {

    List<DominoGameRoundDTO> getDominoGameRoundDTOs(DominoGame game);

}
