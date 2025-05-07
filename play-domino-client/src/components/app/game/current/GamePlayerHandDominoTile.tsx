import {DominoTile} from "../../../../models/game.models.ts";
import {ButtonHTMLAttributes, FC} from "react";
import GameDominoTile from "./GameDominoTile.tsx";

interface DominoTileProps extends ButtonHTMLAttributes<HTMLDivElement> {
    tile: DominoTile;
    orientation: "vertical" | "horizontal";
}

const GamePlayerHandDominoTile: FC<DominoTileProps> = ({
                                                           tile,
                                                           orientation = 'horizontal',
                                                           ...props
                                                       }) => {

    return (
        <div
            draggable
            onDragStart={() => console.log(`Arrastando peça: ${tile}`)} {...props} className="h-16">
            <GameDominoTile tile={tile}
                            width="w-9"
                            height="h-18"
                            orientation={orientation}
            />
        </div>
    );
}

export default GamePlayerHandDominoTile;