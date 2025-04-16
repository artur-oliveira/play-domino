package org.playdomino.services.game;

import lombok.RequiredArgsConstructor;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoPlayer;
import org.playdomino.models.game.dto.AddPlayerToGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.JoinDominoGame;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.services.game.validation.addplayer.BeforeAddPlayerService;
import org.playdomino.services.game.validation.create.BeforeCreateGameService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DominoGameServiceImpl implements DominoGameService {

    private final DominoGameRepository dominoGameRepository;
    private final List<BeforeCreateGameService> beforeCreateGameServiceList;
    private final List<BeforeAddPlayerService> beforeAddPlayerServices;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame create(final CreateDominoGame game) {
        DominoGame currentGame = game.toGame(passwordEncoder);
        beforeCreateGameServiceList.forEach(service -> service.process(currentGame));
        DominoGame savedGame = dominoGameRepository.save(currentGame);
        savedGame = addPlayerToDominoGame(AddPlayerToGame.builder().game(savedGame).user(savedGame.getHost()).password(game.getPassword()).build());
        return savedGame;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame join(final JoinDominoGame join) {
        return addPlayerToDominoGame(AddPlayerToGame
                .builder()
                .game(join.getGame())
                .password(join.getPassword())
                .user((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .build());
    }

    @Transactional(rollbackFor = Exception.class)
    public DominoGame addPlayerToDominoGame(final AddPlayerToGame addPlayerToGame) {
        final DominoGame game = addPlayerToGame.getGame();
        beforeAddPlayerServices.forEach(service -> service.process(addPlayerToGame));
        game.getPlayers().add(addPlayerToGame.toDominoPlayer());
        return dominoGameRepository.saveAndFlush(game);
    }
}
