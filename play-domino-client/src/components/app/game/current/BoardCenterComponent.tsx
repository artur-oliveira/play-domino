import React, {FC, useMemo} from "react";
import {DominoGameMove, DominoGameResponse, DominoTile, MoveDirection} from "../../../../models/game.models.ts";
import {isDoubleTile, tileValues} from "../../../../utils/dominoTiles.ts";
import GameDominoTile from "./GameDominoTile.tsx";

type BoardCenterComponentProps = {
  game: DominoGameResponse;
  onTileDrop?: (tile: DominoTile, index: number) => void;
};

const BoardCenterComponent: FC<BoardCenterComponentProps> = ({game, onTileDrop}) => {
  const moves: Partial<DominoGameMove>[] = useMemo(() => {
    const rounds = game.rounds || [];
    return rounds.length > 0 ? rounds[rounds.length - 1].moves : [];
  }, [game]);

  const orderedTiles = useMemo(() => {
    const tiles: Partial<DominoGameMove>[] = [];
    (moves || []).forEach(move => {
      if (move.moveDirection === "RIGHT") tiles.push(move);
      else tiles.unshift(move);
    });
    return tiles;
  }, [moves]);

  const {leftValue, rightValue} = useMemo(() => {
    if (!orderedTiles.length) return {leftValue: null, rightValue: null};

    const firstTile = orderedTiles[0].tilePlayed!;
    let [left, right] = tileValues(firstTile);

    orderedTiles.slice(1).forEach(move => {
      const [a, b] = tileValues(move.tilePlayed!);

      if (move.moveDirection === "RIGHT") {
        // conectar lado esquerdo do a/b à ponta direita
        if (a === right) right = b;
        else right = a;
      } else {
        // conectar lado direito do a/b à ponta esquerda
        if (b === left) left = a;
        else left = b;
      }
    });

    return {leftValue: left, rightValue: right};
  }, [orderedTiles]);

  const getDominoTileMoveOrientation = (moveIndex: number) => {
    const move = orderedTiles[moveIndex];
    const tile = move.tilePlayed!;
    const [a, b] = tileValues(tile);

    if (isDoubleTile(tile)) return "vertical";

    if (moveIndex === 0) return "horizontal"; // primeira peça

    // decidir se precisa virar
    if (move.moveDirection === "RIGHT") {
      // ponta da esquerda do tile deve bater com rightValue anterior
      return a === rightValue ? "horizontal" : "horizontalflipped";
    } else {
      // ponta da direita do tile deve bater com leftValue anterior
      return b === leftValue ? "horizontal" : "horizontalflipped";
    }
  };

  const handleDrop = (e: React.DragEvent<HTMLDivElement>, moveDirection: MoveDirection) => {
    e.preventDefault();
    console.log(`Movimento na ${moveDirection}`);

    const raw = e.dataTransfer.getData("tile");
    if (!raw) return;
    try {
      const {tile, index} = JSON.parse(raw);
      if (tile && typeof index === "number") {
        onTileDrop?.(tile, index);
      }
    } catch {
      // ignore parse errors
    }
  };

  return (
    <div className="bg-zinc-800 border border-zinc-600 rounded-2xl p-4 relative w-full h-80 flex items-center justify-center">
      {/* Zona esquerda */}
      <div
        className="absolute left-0 top-0 w-1/2 h-full"
        onDragOver={(e) => e.preventDefault()}
        onDrop={(e) => handleDrop(e, 'LEFT')}
      />

      {/* Zona direita */}
      <div
        className="absolute right-0 top-0 w-1/2 h-full"
        onDragOver={(e) => e.preventDefault()}
        onDrop={(e) => handleDrop(e, 'RIGHT')}
      />

      {/* Peças do tabuleiro */}
      {moves.length === 0 ? (
        <p className="text-sm text-zinc-400">Nenhuma peça jogada ainda.</p>
      ) : (
        orderedTiles.map((move, index) => (
          <GameDominoTile
            key={`${move.tilePlayed}`}
            tile={move.tilePlayed!}
            orientation={getDominoTileMoveOrientation(index)}
          />
        ))
      )}
    </div>
  );
};

export default BoardCenterComponent;
