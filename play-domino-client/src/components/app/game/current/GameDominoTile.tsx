import {DominoTile} from "../../../../models/game.models.ts";
import {FC} from "react";
import {DominoTileSvg} from "../DominoTileSvgLoader.tsx";
import {tiles} from "../../../../utils/dominoTiles.ts";

interface DominoTileProps {
    tile: DominoTile;
    orientation: 'vertical' | 'horizontal' | 'flipped';
    width?: string;
    height?: string;
}

const GameDominoTile: FC<DominoTileProps> = ({
                                                 tile,
                                                 orientation = 'horizontal',
                                                 width,
                                                 height,
                                             }) => {

    const rotate = orientation === 'vertical' ? '' : orientation === 'horizontal' ? 'rotate-90' : orientation === 'flipped' ? 'rotate-270' : ''

    const w = width || (orientation === 'vertical' ? 'w-6' : 'w-12');
    const h = height || 'h-12';

    console.log(tile, w, h)

    return (
        <DominoTileSvg name={tiles[tile]} className={`${w} ${h} ${rotate}`}/>
    );
}

export default GameDominoTile;