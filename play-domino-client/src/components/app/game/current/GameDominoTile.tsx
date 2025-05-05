import {DominoTile} from "../../../../models/game.models.ts";
import {ButtonHTMLAttributes, FC} from "react";
import {tiles} from "../../../../utils/dominoTiles.ts";

interface DominoTileProps extends ButtonHTMLAttributes<HTMLDivElement> {
    tile: DominoTile;
    orientation: "vertical" | "horizontal";
}

const GameDominoTile: FC<DominoTileProps> = ({
                                                 tile,
                                                 orientation = 'horizontal',
                                                 ...props
                                             }) => {

    const rotate = orientation === 'vertical' ? 'rotate-90' : '';
    return (
        <div
            className={`w-12 h-16 bg-zinc-500 rounded-lg flex justify-center items-center cursor-grab active:cursor-grabbing`}
            draggable
            onDragStart={() => console.log(`Arrastando peça: ${tile}`)}
            {...props}
        >
            <span className={`${rotate} text-4xl`}>{String.fromCodePoint(tiles[tile])}</span>
        </div>
    );
}

export default GameDominoTile;