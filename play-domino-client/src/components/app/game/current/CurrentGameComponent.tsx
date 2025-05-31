import {useUser} from "../../../../providers/user/useUser.tsx";
import {ErrorUtils} from "../../../../utils/errorUtils.ts";
import {useCancelGame, useExitGame, useGetOngoingGame} from "../../../../api/game.api.ts";
import GameHeaderComponent from "./GameHeaderComponent.tsx";
import DominoBoardComponent from "./DominoBoardComponent.tsx";
import {Skeleton} from "../../../generic/Skeleton.tsx";
import {toast} from "sonner";
import {useModal} from "../../../../providers/useModal.ts";
import CancelGameModalComponent from "./CancelGameModalComponent.tsx";
import ExitGameModalComponent from "./ExitGameModalComponent.tsx";
import {useNavigate} from "react-router-dom";
import {useWebSocketContext} from "../../../../providers/websocket/useWebSocket.tsx";
import {useEffect} from "react";

const CurrentGameComponent = () => {
    const {user} = useUser();
    const onGoingGame = useGetOngoingGame();
    const cancelGame = useCancelGame();
    const exitGame = useExitGame();
    const cancelModal = useModal();
    const exitModal = useModal();
    const navigate = useNavigate();
    const {subscribeToTopic, unsubscribeFromTopic, messages} = useWebSocketContext();

    const game = onGoingGame.data;

    // ✅ Inscrição segura no WebSocket
    useEffect(() => {
        if (!game) return;

        const topic = `/topic/game.${game.id}`;
        subscribeToTopic(topic);

        return () => {
            unsubscribeFromTopic(topic);
        };
    }, [game, subscribeToTopic, unsubscribeFromTopic]);

    // ✅ Refetch quando receber mensagens
    useEffect(() => {
        if (!game) return;

        const topic = `/topic/game.${game.id}`;
        if ((messages[topic] || []).length > 0) {
            void onGoingGame.refetch();
        }
    }, [messages, game, onGoingGame]);

    if (onGoingGame.isLoading) {
        return (
            <>
                <div className="rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
                    <Skeleton className="p-4 h-36 w-full"/>
                </div>
                <div className="rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
                    <Skeleton className="p-4 h-96 w-full"/>
                </div>
            </>
        )
    }

    if (onGoingGame.isError) {
        ErrorUtils.displayAxiosError(onGoingGame.error);
        return (
            <div className="p-4 text-center text-red-500">Erro ao carregar o jogo.</div>
        );
    }

    if (game === null || game === undefined || !user) {
        return (<div className="rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center italic text-center">
            Nenhum jogo em andamento!
        </div>);
    }

    const handleShare = () => {
        const baseUrl = `${window.location.origin}/app/game/invite/${game.inviteCode}`;
        navigator.clipboard.writeText(baseUrl)
            .catch(() => {
                toast.error('Erro ao copiar link de convite!');
            })
            .then(() => {
                toast.success('Link de convite copiado com sucesso!');
            });
    }

    const handleCancel = (
        approve: boolean,
    ) => {
        cancelGame.mutate({
            approve: approve,
            gameId: game.id
        }, {
            onSuccess: (game) => {
                if (game.status === 'CANCELLED') {
                    handleCanceledGame();
                } else {
                    toast.success('Cancelamento recebido. Aguarde os outros jogadores!');
                }
            },
            onError: (err) => {
                ErrorUtils.displayAxiosError(err);
            }
        });
    }

    const handleExit = () => {
        exitGame.mutate(game.id, {
            onSuccess: () => {
                navigate("/app");
            },
            onError: (err) => {
                ErrorUtils.displayAxiosError(err);
            }
        });
    }

    const handleCanceledGame = () => {
        toast.success('O jogo foi cancelado com sucesso!');
        navigate("/app");
    }

    return (
        <>
            <div
                className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
                <div className="p-4">
                    <GameHeaderComponent game={game} onCancel={cancelModal.openModal} onShare={handleShare}
                                         onExit={exitModal.openModal}/>
                </div>
            </div>
            <div
                className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
                <div className="p-4">
                    <DominoBoardComponent game={game}/>
                </div>
            </div>
            <CancelGameModalComponent isOpen={cancelModal.isOpen}
                                      onClose={cancelModal.closeModal}
                                      label={game.currentHost ? 'Deseja cancelar o jogo?' : 'Aceita o cancelamento do jogo?'}
                                      onConfirm={handleCancel}/>
            <ExitGameModalComponent isOpen={exitModal.isOpen}
                                    onClose={exitModal.closeModal}
                                    onConfirm={handleExit}/>

        </>
    );
};

export default CurrentGameComponent;
