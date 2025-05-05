import {useUser} from "../../../../providers/user/useUser.tsx";
import {ErrorUtils} from "../../../../utils/errorUtils.ts";
import {useGetOngoingGame} from "../../../../api/game.api.ts";
import GameHeaderComponent from "./GameHeaderComponent.tsx";
import DominoBoardComponent from "./DominoBoardComponent.tsx";

const CurrentGameComponent = () => {
    const {user} = useUser();
    const onGoingGame = useGetOngoingGame();

    if (onGoingGame.isLoading) {
        return <div className="p-4 text-center">Carregando jogo...</div>;
    }

    if (onGoingGame.isError) {
        ErrorUtils.displayAxiosError(onGoingGame.error);
        return <div className="p-4 text-center text-red-500">Erro ao carregar o jogo.</div>;
    }
    const game = onGoingGame.data;


    if (game === null || game === undefined || !user) {
        return <div className="p-4 text-center text-red-500">Você não está participando de nenhum jogo!</div>;
    }

    return (
        <>
            <div
                className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
                <div className="p-4">
                    <GameHeaderComponent game={game} onCancel={() => {
                    }} onShare={() => {
                    }}/>
                </div>
            </div>
            <div
                className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
                <div className="p-4">
                    <DominoBoardComponent game={game}/>
                </div>
            </div>
        </>
    );
};

export default CurrentGameComponent;
