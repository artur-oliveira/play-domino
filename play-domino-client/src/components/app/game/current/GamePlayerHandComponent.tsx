import {FC} from "react";
import {DominoGamePlayer, DominoTile} from "../../../../models/game.models.ts";
import GamePlayerHandDominoTile from "./GamePlayerHandDominoTile.tsx";

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
                hand.map((tile) => (
                    <GamePlayerHandDominoTile tile={tile}
                                              orientation="vertical"
                                              onDragStart={() => console.log(`Arrastando peça: ${tile}`)}
                    />
                ))
            )}
        </div>
    );
};

export default GamePlayerHandComponent;
