import {FC, useEffect, useState} from "react";
import {DominoGameResponse, DominoTile, MoveDirection} from "../../../../models/game.models.ts";
import GamePlayersAreaComponent from "./GamePlayersAreaComponent.tsx";
import BoardCenterComponent from "./BoardCenterComponent.tsx";
import GamePlayerHandComponent from "./GamePlayerHandComponent.tsx";
import {useCreateMove} from "../../../../api/game.api.ts";
import {ErrorUtils} from "../../../../utils/errorUtils.ts";

type DominoBoardComponentProps = {
  game: DominoGameResponse;
}

const DominoBoardComponent: FC<DominoBoardComponentProps> = ({game}) => {
  const currentPlayer = game.players.find(p => p.currentUser);
  const [hand, setHand] = useState<DominoTile[]>(currentPlayer?.hand || []);
  const createMove = useCreateMove();

  // sincroniza quando a partida / jogador mudarem
  useEffect(() => {
    setHand(currentPlayer?.hand || []);
  }, [currentPlayer?.hand, game]);

  const handleTileDrop = (tile: DominoTile, index: number, moveDirection: MoveDirection) => {
    createMove.mutate({
      gameId: game.id,
      tile: tile,
      moveDirection: moveDirection,
    }, {
      onSuccess: () => {
        setHand(prev => {
          const copy = [...prev];
          if (index >= 0 && index < copy.length) {
            const t = copy[index];
            if (t === tile) {
              copy.splice(index, 1);
              return copy;
            }
          }
          const found = copy.findIndex(t => t === tile);
          if (found >= 0) copy.splice(found, 1);
          return copy;
        });
      },
      onError: (err) => {
        ErrorUtils.displayAxiosError(err);
      }
    });
  };

  return (
    <div className="bg-zinc-700 p-4 rounded-2xl shadow flex flex-col justify-between h-[55vh]">
      <GamePlayersAreaComponent game={game}/>
      <div className="flex-1 flex justify-center items-center my-4">
        <BoardCenterComponent game={game} onTileDrop={handleTileDrop}/>
      </div>
      {currentPlayer && <GamePlayerHandComponent player={{...currentPlayer, hand}}/>}
    </div>
  );
};

export default DominoBoardComponent;
