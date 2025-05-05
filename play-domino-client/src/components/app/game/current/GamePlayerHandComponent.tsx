import {FC} from "react";
import {DominoGamePlayer, DominoTile} from "../../../../models/game.models.ts";

type PlayerHandComponentProps = {
    player: DominoGamePlayer;
}

const GamePlayerHandComponent: FC<PlayerHandComponentProps> = ({player}) => {
    const hand: DominoTile[] = player.hand || [];
    return (
        <div className="mt-4 flex justify-center gap-2">
            {hand.length === 0 ? (
                <p className="text-sm text-zinc-400">Sua mão está vazia.</p>
            ) : (
                hand.map((piece, index) => (
                    <div
                        key={index}
                        className="w-12 h-20 bg-zinc-500 rounded-lg flex justify-center items-center text-2xl font-bold cursor-grab active:cursor-grabbing"
                        draggable
                        onDragStart={() => console.log(`Arrastando peça: ${piece}`)}
                    >
                        {piece}
                    </div>
                ))
            )}
        </div>
    );
};

export default GamePlayerHandComponent;
