import {FC} from "react";
import {DominoGamePlayer, DominoGameResponse} from "../../../../models/game.models.ts";

type DominoPlayerComponentProps = { game: DominoGameResponse }
const GamePlayersAreaComponent: FC<DominoPlayerComponentProps> = ({game}) => {
    const players = game.players;
    const firstName = (player: DominoGamePlayer) => {
        return (player.user.name || '').split(' ')[0]
    };
    return (
        <div className="flex justify-around items-center">
            {players.map((player) => (
                <div key={player.id} className="flex items-center gap-2 sm:gap-4">
                    <div className={`w-9 h-9 rounded-full flex items-center justify-center font-bold
                                ${player.currentTurn ? 'ring-2 ring-green-400' : ''}
                                bg-[#fdeccd] text-zinc-900`}>
                        {firstName(player).charAt(0)}
                    </div>
                    <div>
                        <h1 className="text-[#fdeccd] text-sm leading-none">{firstName(player)}</h1>
                        {player.currentUser && <span className="text-green-400 text-xs flex items-center gap-1">
                            (Você) </span>}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default GamePlayersAreaComponent;
