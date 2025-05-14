import {Skeleton} from "../../generic/Skeleton.tsx";
import {useGetPublicGames, useJoinGame} from "../../../api/game.api.ts";
import {useEffect} from "react";
import {useWebSocketContext} from "../../../providers/websocket/useWebSocket.tsx";
import GameCardComponent from "./GameCardComponent.tsx";
import {useNavigate} from "react-router-dom";
import {ErrorUtils} from "../../../utils/errorUtils.ts";
import {JoinGame} from "../../../models/game.models.ts";

const PublicGamesComponent = () => {
    const {data: games, isLoading, isError, refetch: refetchGames} = useGetPublicGames();
    const joinGame = useJoinGame();
    const navigate = useNavigate();
    const handleJoin = (join: JoinGame) => {
        joinGame.mutate(join, {
            onSuccess: () => {
                navigate("/app/game");
            },
            onError: (err) => {
                ErrorUtils.displayAxiosError(err);
                if (ErrorUtils.getErrorCode(err) === 'dominogame.add-player.user-already-joined-game') {
                    navigate("/app/game");
                }
            }
        });
    };

    const topic = `/topic/public.games`;
    const {subscribeToTopic, unsubscribeFromTopic, messages} = useWebSocketContext();

    useEffect(() => {
        subscribeToTopic(topic);
        return () => unsubscribeFromTopic(topic);
    }, [topic, subscribeToTopic, unsubscribeFromTopic]);

    useEffect(() => {
        if ((messages[topic] || []).length > 0) void refetchGames();
    }, [messages, topic, refetchGames]);

    return (
        <div>
            <h2 className="text-lg font-semibold mb-4">Salas Públicas</h2>
            {isError && <div className="text-red-400 mb-4">Erro ao carregar as salas.</div>}

            <div className="flex flex-wrap gap-4">
                {isLoading || !games
                    ? Array.from({length: 6}).map((_, idx) => (
                        <Skeleton key={idx} className="h-24 w-64 rounded-xl"/>
                    ))
                    : games.data.length > 0
                        ? games.data.map((game) => (
                            <GameCardComponent
                                key={game.id}
                                game={game}
                                handleJoin={handleJoin}
                                isJoining={joinGame.isPending}
                            />
                        ))
                        : <div className="text-center text-zinc-400 w-full">Nenhuma sala disponível no momento.</div>}
            </div>
        </div>
    );
};

export default PublicGamesComponent;
