package org.playdomino.services.game;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.auth.UserUtils;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.exceptions.game.DominoGameException;
import org.playdomino.exceptions.game.DominoGameExceptionConstants;
import org.playdomino.models.game.DominoGame;
import org.playdomino.models.game.DominoGameVote;
import org.playdomino.models.game.GameStatus;
import org.playdomino.models.game.dto.AddPlayerDominoGame;
import org.playdomino.models.game.dto.CancelDominoGame;
import org.playdomino.models.game.dto.CreateDominoGame;
import org.playdomino.models.game.dto.JoinDominoGame;
import org.playdomino.repositories.game.DominoGameRepository;
import org.playdomino.repositories.game.DominoGameVoteRepository;
import org.playdomino.services.game.process.create.AfterCreateGameService;
import org.playdomino.services.game.process.start.AfterStartGameService;
import org.playdomino.services.game.process.addplayer.BeforeAddPlayerService;
import org.playdomino.services.game.process.cancel.AfterCancelGameService;
import org.playdomino.services.game.process.cancel.BeforeCancelGameService;
import org.playdomino.services.game.process.create.BeforeCreateGameService;
import org.playdomino.services.game.process.vote.AfterGameVoteService;
import org.playdomino.services.game.process.vote.BeforeGameVoteService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.LinkOption;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DominoGameServiceImpl implements DominoGameService {
    private static final String GAME_NOT_FOUND = DominoGameExceptionConstants.GAME_NOT_FOUND;

    private final DominoGameRepository dominoGameRepository;
    private final List<BeforeCreateGameService> beforeCreateGameServiceList;
    private final List<AfterCreateGameService> afterCreateGameServices;
    private final List<BeforeAddPlayerService> beforeAddPlayerServices;

    private final List<BeforeGameVoteService> beforeGameVoteServices;
    private final List<AfterGameVoteService> afterGameVoteServices;

    private final List<BeforeCancelGameService> beforeCancelGameServices;
    private final List<AfterCancelGameService> afterCancelGameServices;

    private final List<AfterStartGameService> afterStartGameServices;

    private final PasswordEncoder passwordEncoder;
    private final MessagesComponent messagesComponent;
    private final DominoGamePlayerService dominoGamePlayerService;
    private final DominoGameVoteRepository dominoGameVoteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DominoGame> findAllPublicGames(Integer page, Integer size) {
        List<Long> publicGamesIds = dominoGameRepository.findIdPublicUnfinishedGamesByHostNotEqual(
                UserUtils.currentUser(),
                GameStatus.unfinisheds(),
                PageRequest.of(
                        Math.max(Optional.ofNullable(page).map(it -> it - 1).orElse(0), 0),
                        Math.min(Optional.ofNullable(size).orElse(10), 50)
                )
        );
        if (publicGamesIds.isEmpty()) {
            return Collections.emptyList();
        }
        return dominoGameRepository.findAllByIdIn(publicGamesIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DominoGame> findCurrentDominoGame() {
        return dominoGameRepository.findDominoGameByStatusAndPlayerUser(
                GameStatus.unfinisheds(),
                UserUtils.currentUser()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame create(final CreateDominoGame gameRequest) {
        DominoGame game = createInitialGame(gameRequest);
        game = addHostPlayer(game, gameRequest.getPassword());
        processAfterCreateGame(game);
        return game;
    }

    @Override
    @Transactional(readOnly = true)
    public DominoGame findDominoGameById(final Long id) {
        return dominoGameRepository.findById(id)
                .orElseThrow(this::createGameNotFoundException);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame join(final JoinDominoGame joinRequest) {
        DominoGame game = findDominoGameById(joinRequest.getGameId());
        return addPlayerToDominoGame(createAddPlayerRequest(game, joinRequest));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame start(Long gameId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DominoGame cancel(final CancelDominoGame cancelRequest) {
        DominoGame game = findDominoGameById(cancelRequest.getGameId());
        cancelRequest.setGame(game);

        processBeforeGameVote(game);
        processBeforeCancelGame(cancelRequest);

        DominoGameVote vote = processGameCancellationVote(game, cancelRequest);
        setGameCanceledIfApproved(game, vote);

        processAfterGameVote(game, vote);
        processAfterCancelGame(game, vote);

        return game;
    }

    @Transactional(rollbackFor = Exception.class)
    DominoGame addPlayerToDominoGame(final AddPlayerDominoGame addPlayerRequest) {
        DominoGame game = addPlayerRequest.getGame();
        processBeforeAddPlayerRequest(addPlayerRequest);
        game.getPlayers().add(addPlayerRequest.toDominoPlayer());
        game = dominoGameRepository.saveAndFlush(game);
        return game;
    }

    @Transactional(rollbackFor = Exception.class)
    DominoGame createInitialGame(CreateDominoGame gameRequest) {
        DominoGame game = gameRequest.toGame(passwordEncoder);
        beforeCreateGameServiceList.forEach(service -> service.process(game));
        return dominoGameRepository.save(game);
    }

    @Transactional(rollbackFor = Exception.class)
    DominoGame addHostPlayer(DominoGame game, String password) {
        AddPlayerDominoGame addPlayerRequest = AddPlayerDominoGame.builder()
                .game(game)
                .user(game.getHost())
                .password(password)
                .build();
        return addPlayerToDominoGame(addPlayerRequest);
    }

    private AddPlayerDominoGame createAddPlayerRequest(DominoGame game, JoinDominoGame joinRequest) {
        return AddPlayerDominoGame.builder()
                .game(game)
                .password(joinRequest.getPassword())
                .user(UserUtils.currentUser())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    void processBeforeAddPlayerRequest(AddPlayerDominoGame request) {
        beforeAddPlayerServices.forEach(service -> service.process(request));
    }

    @Transactional(rollbackFor = Exception.class)
    void processBeforeGameVote(DominoGame game) {
        beforeGameVoteServices.forEach(service -> service.process(game));
    }

    @Transactional(rollbackFor = Exception.class)
    void processAfterCreateGame(DominoGame game) {
        afterCreateGameServices.forEach(service -> service.process(game));
    }

    @Transactional(rollbackFor = Exception.class)
    void processAfterGameVote(DominoGame game, DominoGameVote vote) {
        afterGameVoteServices.forEach(service -> service.process(game, vote));
    }

    @Transactional(rollbackFor = Exception.class)
    void processAfterCancelGame(DominoGame game, DominoGameVote vote) {
        afterCancelGameServices.forEach(service -> service.process(game));
    }

    @Transactional(rollbackFor = Exception.class)
    void processBeforeCancelGame(CancelDominoGame cancelRequest) {
        beforeCancelGameServices.forEach(service -> service.process(cancelRequest));
    }

    @Transactional(rollbackFor = Exception.class)
    DominoGameVote processGameCancellationVote(DominoGame game, CancelDominoGame cancelRequest) {
        DominoGameVote vote = cancelRequest.toDominoGameVote(
                game, dominoGamePlayerService.getPlayerInGame(game)
        );
        dominoGameVoteRepository.save(vote);
        game.getVotes().add(vote);
        return vote;
    }

    private void setGameCanceledIfApproved(DominoGame game, DominoGameVote vote) {
        if (vote.getVoteType().isApproved(game)) {
            game.setStatus(GameStatus.CANCELLED);
        }
    }

    private DominoGameException createGameNotFoundException() {
        return new DominoGameException(GAME_NOT_FOUND,
                messagesComponent.getMessage(GAME_NOT_FOUND));
    }
}
