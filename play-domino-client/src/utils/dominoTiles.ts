import {DominoTile, MoveDirection} from "../models/game.models.ts";

const doubleTiles: Set<DominoTile> = new Set<DominoTile>(['ZERO_ZERO', 'ONE_ONE', 'TWO_TWO', 'THREE_THREE', 'FOUR_FOUR', 'FIVE_FIVE', 'SIX_SIX']);
export const tiles: Record<DominoTile, string> = {
    ZERO_ZERO: '0_0',
    ZERO_ONE: '0_1',
    ZERO_TWO: '0_2',
    ZERO_THREE: '0_3',
    ZERO_FOUR: '0_4',
    ZERO_FIVE: '0_5',
    ZERO_SIX: '0_6',
    ONE_ONE: '1_1',
    ONE_TWO: '1_2',
    ONE_THREE: '1_3',
    ONE_FOUR: '1_4',
    ONE_FIVE: '1_5',
    ONE_SIX: '1_6',
    TWO_TWO: '2_2',
    TWO_THREE: '2_3',
    TWO_FOUR: '2_4',
    TWO_FIVE: '2_5',
    TWO_SIX: '2_6',
    THREE_THREE: '3_3',
    THREE_FOUR: '3_4',
    THREE_FIVE: '3_5',
    THREE_SIX: '3_6',
    FOUR_FOUR: '4_4',
    FOUR_FIVE: '4_5',
    FOUR_SIX: '4_6',
    FIVE_FIVE: '5_5',
    FIVE_SIX: '5_6',
    SIX_SIX: '6_6',
}

export const isDoubleTile = (tile: DominoTile): boolean => {
    return doubleTiles.has(tile);
}

export const shouldFlipTile = (
    currentTile: DominoTile, previousTile: DominoTile, moveDirection: MoveDirection
): boolean => {
    const [currentLeft, currentRight] = currentTile.split('_');
    const [prevLeft, prevRight] = previousTile.split('_');

    if (moveDirection === 'RIGHT') {
        return currentLeft !== prevRight;
    } else {
        // Na esquerda, queremos que o lado direito da nova pedra se conecte com o lado esquerdo da anterior
        return currentRight !== prevLeft;
    }
}