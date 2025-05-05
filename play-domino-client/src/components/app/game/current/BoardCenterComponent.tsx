import {DominoGameResponse} from "../../../../models/game.models.ts";
import {FC} from "react";

type BoardCenterComponentProps = { game: DominoGameResponse };

const BoardCenterComponent: FC<BoardCenterComponentProps> = ({game}) => {
    return (
        <div
            className="bg-zinc-800 border border-zinc-600 rounded-2xl p-4 min-w-[300px] min-h-[150px] flex flex-wrap justify-center items-center gap-2">
            {game.moves.length === 0 ? (
                <p className="text-sm text-zinc-400">Nenhuma peça jogada ainda.</p>
            ) : (
                game.moves.map((move, index) => (
                    <div
                        key={index}
                        className="w-12 h-20 bg-zinc-500 rounded-lg flex justify-center items-center text-2xl font-bold"
                    >
                        {move.tilePlayed} {/* Ajuste conforme sua estrutura */}
                    </div>
                ))
            )}
        </div>
    );
};

export default BoardCenterComponent;
