import {FC} from "react";
import {DominoGamePlayer} from "../../models/game.models.ts";

type DominoPlayerComponentProps = {
    player: DominoGamePlayer;
    isCurrentTurn: boolean;
}
const DominoPlayerComponent: FC<DominoPlayerComponentProps> = ({
                                                                   player,
                                                                   isCurrentTurn
                                                               }) => {
    return (
        <div
            className={`
                flex flex-col items-center justify-center
                bg-zinc-700 rounded-xl shadow
                w-28 h-32 p-3
                ${isCurrentTurn ? 'ring-2 ring-yellow-400' : ''}
            `}
        >
            {/* Avatar */}
            <div className="w-12 h-12 bg-zinc-500 rounded-full flex items-center justify-center text-lg font-bold text-[#fdeccd] mb-2">
                {player.user.name[0]}
            </div>
            {/* Nome */}
            <div className="text-sm font-semibold text-[#fdeccd] text-center">{player.user.name}</div>
        </div>
    );
};

export default DominoPlayerComponent;