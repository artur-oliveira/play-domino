package org.playdomino.services.game;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.auth.User;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.JoinDominoGame;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.services.game.validation.addplayer.BeforeAddPlayerService;
import org.playdomino.services.game.validation.cancel.BeforeCancelGameService;
import org.playdomino.services.game.validation.create.BeforeCreateGameService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DominoGameServiceImpl implements DominoGameService {

    private final DominoGameRepository dominoGameRepository;
    private final List<BeforeCreateGameService> beforeCreateGameServiceList;
    private final List<BeforeAddPlayerService> beforeAddPlayerServices;
    private final List<BeforeCancelGameService> beforeCancelGameServices;
    private final PasswordEncoder passwordEncoder;
    private final MessagesComponent messagesComponent;

    @Override
    @Transactional(readOnly = true)
    public Optional<DominoGame> findCurrentDominoGame() {
        return dominoGameRepository.findDominoGameByStatusAndPlayerUser(
                GameStatus.unfinisheds(),
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame create(final CreateDominoGame game) {
        DominoGame currentGame = game.toGame(passwordEncoder);
        beforeCreateGameServiceList.forEach(service -> service.process(currentGame));
        DominoGame savedGame = dominoGameRepository.save(currentGame);
        savedGame = addPlayerToDominoGame(AddPlayerDominoGame.builder().game(savedGame).user(savedGame.getHost()).password(game.getPassword()).build());
        return savedGame;
    }

    @Override
    @Transactional(readOnly = true)
    public DominoGame findDominoGameById(final Long id) {
        return dominoGameRepository.findById(id).orElseThrow(() -> new DominoGameException(DominoGameExceptionConstants.GAME_NOT_FOUND, messagesComponent.getMessage(DominoGameExceptionConstants.GAME_NOT_FOUND)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame join(final JoinDominoGame join) {
        return addPlayerToDominoGame(AddPlayerDominoGame
                .builder()
                .game(findDominoGameById(join.getGameId()))
                .password(join.getPassword())
                .user((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame start(Long gameId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame cancel(final CancelDominoGame cancelDominoGame) {
        final DominoGame game = findDominoGameById(cancelDominoGame.getGameId());
        beforeCancelGameServices.forEach(service -> service.process(game));
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public DominoGame addPlayerToDominoGame(final AddPlayerDominoGame addPlayerDominoGame) {
        final DominoGame game = addPlayerDominoGame.getGame();
        beforeAddPlayerServices.forEach(service -> service.process(addPlayerDominoGame));
        game.getPlayers().add(addPlayerDominoGame.toDominoPlayer());
        return dominoGameRepository.saveAndFlush(game);
    }
}
