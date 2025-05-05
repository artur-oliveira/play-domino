import {DominoGameResponse} from "../../../../models/game.models.ts";
import {FC} from "react";
import GamePlayersAreaComponent from "./GamePlayersAreaComponent.tsx";
import BoardCenterComponent from "./BoardCenterComponent.tsx";
import GamePlayerHandComponent from "./GamePlayerHandComponent.tsx";

type DominoBoardComponentProps = {
    game: DominoGameResponse;
}
const DominoBoardComponent: FC<DominoBoardComponentProps> = ({
                                                                 game
                                                             }) => {
    const currentPlayer = game.players.find(p => p.currentUser);
    return (
        <div className="bg-zinc-700 p-4 rounded-2xl shadow flex flex-col justify-between h-[55vh]">
            <GamePlayersAreaComponent game={game}/>
            <div className="flex-1 flex justify-center items-center my-4">
                <BoardCenterComponent game={game}/>
            </div>
            {currentPlayer && <GamePlayerHandComponent player={currentPlayer}/>}
        </div>
    );
}

export default DominoBoardComponent;