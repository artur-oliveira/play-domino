import {FC} from "react";
import {useGetInviteGame, useJoinGame} from "../../../api/game.api.ts";
import {Skeleton} from "../../generic/Skeleton.tsx";
import {useNavigate} from "react-router-dom";
import {ErrorUtils} from "../../../utils/errorUtils.ts";
import GameCardComponent from "../home/GameCardComponent.tsx";
import {JoinGame} from "../../../models/game.models.ts";

type DominoInviteGameComponentProps = {
    inviteCode: string;
};

const DominoInviteGameComponent: FC<DominoInviteGameComponentProps> = ({
                                                                           inviteCode,
                                                                       }) => {
    const {data, isLoading, isError} = useGetInviteGame(inviteCode);
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

    if (isLoading) return <Skeleton className="w-full h-40 rounded-2xl"/>;

    if (isError || !data) {
        return (
            <p className="text-red-400 text-center">
                Convite inválido ou expirado.
            </p>
        );
    }

    return (
        <div className="text-[#fdeccd] flex flex-col items-center space-y-6">
            <div className="text-center space-y-2">
                <h2 className="text-xl font-bold">🎲 Partida Encontrada</h2>
                <p className="text-sm">Você foi convidado para uma partida de dominó.</p>
            </div>

            <GameCardComponent
                key={data.id}
                game={data}
                handleJoin={handleJoin}
                isJoining={joinGame.isPending}
            />
        </div>
    );
};

export default DominoInviteGameComponent;
