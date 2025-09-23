import {DominoTile} from "../../../../models/game.models.ts";
import React, {FC} from "react";
import GameDominoTile from "./GameDominoTile.tsx";

interface DominoTileProps {
  tile: DominoTile;
  index: number;
  orientation?: "vertical" | "horizontal";
  onDragStartIndex?: (index: number) => void;
  onDragEnd?: () => void;
}

const GamePlayerHandDominoTile: FC<DominoTileProps> = ({
                                                         tile,
                                                         index,
                                                         orientation = "horizontal",
                                                         onDragStartIndex,
                                                         onDragEnd
                                                       }) => {

  const handleDragStart = (e: React.DragEvent<HTMLDivElement>) => {
    e.dataTransfer.setData("tile", JSON.stringify({tile, index}));
    e.dataTransfer.effectAllowed = "move";
    onDragStartIndex?.(index);
  };

  return (
    <div
      draggable
      onDragStart={handleDragStart}
      onDragEnd={onDragEnd}
      className="h-16"
    >
      <GameDominoTile tile={tile} width="w-9" height="h-18" orientation={orientation}/>
    </div>
  );
}

export default GamePlayerHandDominoTile;
