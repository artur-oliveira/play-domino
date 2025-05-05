import React, {FC} from "react";
import {DominoTile} from "../../models/game.models";

type DominoTileComponentProps = {
    handleDragStart: (event: React.DragEvent<HTMLDivElement>, tile: DominoTile) => void;
    tile: DominoTile;
}
const DominoTileComponent: FC<DominoTileComponentProps> = ({tile, handleDragStart}) => {
    return (
        <div
            draggable
            onDragStart={(e) => handleDragStart(e, tile)}
            className="w-10 h-16 bg-zinc-600 border border-zinc-300 rounded flex flex-col justify-between items-center p-1 cursor-grab active:cursor-grabbing shadow hover:ring-2 ring-yellow-400"
        >
            <span className="text-lg font-bold">{tile[0].split("_")}</span>
            <span className="text-lg font-bold">{tile[1].split("_")}</span>
        </div>
    );
}

export default DominoTileComponent;