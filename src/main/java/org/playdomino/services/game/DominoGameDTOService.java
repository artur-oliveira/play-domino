package org.playdomino.services.game;

import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.dto.DominoGameDTO;

import java.util.List;

public interface DominoGameDTOService {

    DominoGameDTO getDominoGameDTO(DominoGame game);

}
