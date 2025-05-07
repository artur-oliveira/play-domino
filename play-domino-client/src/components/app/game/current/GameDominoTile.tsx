import {DominoTile} from "../../../../models/game.models.ts";
import {FC} from "react";
import {DominoTileSvg} from "../DominoTileSvgLoader.tsx";
import {tiles} from "../../../../utils/dominoTiles.ts";

interface DominoTileProps {
    tile: DominoTile;
    orientation: 'vertical' | 'horizontal' | 'verticalflipped' | 'horizontalflipped';
    width?: string;
    height?: string;
    className?: string;
}

const GameDominoTile: FC<DominoTileProps> = ({
                                                 tile,
                                                 orientation = 'horizontal',
                                                 width,
                                                 height,
                                                 className,
                                             }) => {

    const getTransformSvg = () => {
        switch (orientation) {
            case 'vertical':
                return '';
            case 'horizontal':
                return 'matrix(6.123233995736766e-17,1,-1,6.123233995736766e-17,0,0)';
            case 'verticalflipped':
                return 'matrix(-1,1.2246467991473532e-16,-1.2246467991473532e-16,-1,0,0)'
            case 'horizontalflipped':
                return 'matrix(-1.8369701987210297e-16,-1,1,-1.8369701987210297e-16,0,0)'

        }
    }

    const transform = getTransformSvg();

    const w = width || (orientation === 'vertical' || orientation === 'verticalflipped' ? 'w-6' : 'w-12');
    const h = height || 'h-12';

    return (
        <DominoTileSvg name={tiles[tile]} className={`${w} ${h} ${className || ''}`} transform={transform}/>
    );
}

export default GameDominoTile;