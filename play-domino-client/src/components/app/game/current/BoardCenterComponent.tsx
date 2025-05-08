import {DominoGameMove, DominoGameResponse} from "../../../../models/game.models.ts";
import {FC, useMemo} from "react";
import {isDoubleTile} from "../../../../utils/dominoTiles.ts";
import GameDominoTile from "./GameDominoTile.tsx";

type BoardCenterComponentProps = { game: DominoGameResponse };

const BoardCenterComponent: FC<BoardCenterComponentProps> = ({game}) => {
    const moves: Partial<DominoGameMove>[] = useMemo(() => {
        return game.moves || [];
    }, [game.moves]);

    // Ordenação respeitando lado jogado
    const orderedTiles = useMemo(() => {

        const tiles: Partial<DominoGameMove>[] = [];
        (moves || []).forEach((move) => {
            if (move.moveDirection === 'RIGHT') {
                tiles.push(move);
            } else if (move.moveDirection === 'LEFT') {
                tiles.unshift(move);
            }
        });
        return tiles;
    }, [moves]);

    const getDominoTileMoveOrientation = (moveIndex: number) => {
        const currentMove = orderedTiles[moveIndex];
        if (isDoubleTile(currentMove.tilePlayed!)) {
            return 'vertical';
        }
        return 'horizontal';
    }

    return (
        <div
            className="bg-zinc-800 border border-zinc-600 rounded-2xl p-4 w-full h-80 flex items-center justify-center">
            {moves.length === 0 ? (
                <p className="text-sm text-zinc-400">Nenhuma peça jogada ainda.</p>
            ) : (
                orderedTiles.map((move, index) => (
                    <GameDominoTile
                        tile={move.tilePlayed!}
                        orientation={getDominoTileMoveOrientation(index)}
                    />
                ))
            )}
        </div>
    );
};

export default BoardCenterComponent;
