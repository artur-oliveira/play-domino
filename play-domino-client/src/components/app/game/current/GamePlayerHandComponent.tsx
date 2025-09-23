import {FC, useState} from "react";
import {DominoGamePlayer, DominoTile} from "../../../../models/game.models.ts";
import GamePlayerHandDominoTile from "./GamePlayerHandDominoTile.tsx";
import {motion, AnimatePresence} from "framer-motion";

type PlayerHandComponentProps = {
  player: DominoGamePlayer; // aqui player.hand deve ser a mão controlada (do parent)
}

const GamePlayerHandComponent: FC<PlayerHandComponentProps> = ({player}) => {
  const hand: DominoTile[] = player.hand || [];
  const [draggingIndex, setDraggingIndex] = useState<number | null>(null);

  const handleDragStart = (index: number) => {
    setDraggingIndex(index);
  };

  const handleDragEnd = () => {
    // se não houver remoção confirmada pelo parent, a peça anima de volta
    setDraggingIndex(null);
  };

  return (
    <div className="mt-4 flex justify-center gap-2">
      {hand.length === 0 ? (
        <p className="text-sm text-zinc-400">Sua mão está vazia.</p>
      ) : (
        <AnimatePresence initial={false}>
          {hand.map((tile, idx) => (
            <motion.div
              key={`tile-${tile}`}
              layout
              initial={{opacity: 1, y: 0}}
              animate={draggingIndex === idx ? {opacity: 0, y: -16, scale: 0.95} : {opacity: 1, y: 0, scale: 1}}
              exit={{opacity: 0, y: 20, scale: 0.95}}
              transition={{duration: 0.16}}
            >
              <GamePlayerHandDominoTile
                tile={tile}
                index={idx}
                orientation="vertical"
                onDragStartIndex={handleDragStart}
                onDragEnd={handleDragEnd}
              />
            </motion.div>
          ))}
        </AnimatePresence>
      )}
    </div>
  );
};

export default GamePlayerHandComponent;
