// BoardCenterComponent.tsx
import React, {FC, useMemo} from "react";
import {
  DominoGameMove,
  DominoGameResponse,
  DominoTile,
  MoveDirection,
} from "../../../../models/game.models.ts";
import {isDoubleTile, tileValues} from "../../../../utils/dominoTiles.ts";
import GameDominoTile from "./GameDominoTile.tsx";

type Orientation = "vertical" | "horizontal" | "horizontalflipped";

type BoardCenterComponentProps = {
  game: DominoGameResponse;
  onTileDrop?: (
    tile: DominoTile,
    index: number,
    moveDirection: MoveDirection
  ) => void;
};

const BoardCenterComponent: FC<BoardCenterComponentProps> = ({
                                                               game,
                                                               onTileDrop,
                                                             }) => {
  // moves em ordem cronológica (do turno 1 em diante)
  const movesChronological: Partial<DominoGameMove>[] = useMemo(() => {
    const rounds = game.rounds || [];
    const moves = rounds.length > 0 ? rounds[rounds.length - 1].moves || [] : [];
    return [...moves].sort((a, b) => (a.turn ?? 0) - (b.turn ?? 0));
  }, [game]);

  // separa primeira peça, lado esquerdo e lado direito
  const {middleMove, leftChron, rightChron} = useMemo(() => {
    if (!movesChronological.length) {
      return {
        middleMove: null,
        leftChron: [] as Partial<DominoGameMove>[],
        rightChron: [] as Partial<DominoGameMove>[],
      };
    }
    const [first, ...rest] = movesChronological;
    return {
      middleMove: first,
      leftChron: rest.filter((m) => m.moveDirection === "LEFT"),
      rightChron: rest.filter((m) => m.moveDirection === "RIGHT"),
    };
  }, [movesChronological]);

  // função para simular encaixes de peças de um lado
  const simulateSide = (
    moves: Partial<DominoGameMove>[],
    initialVal: number,
    side: "LEFT" | "RIGHT"
  ): { display: Partial<DominoGameMove>[]; orientations: Orientation[] } => {
    let openVal = initialVal;
    const oris: Orientation[] = [];

    moves.forEach((m) => {
      const tile = m.tilePlayed!;
      const [a, b] = tileValues(tile);

      if (isDoubleTile(tile)) {
        oris.push("vertical");
        openVal = a; // ambos iguais
        return;
      }

      if (side === "LEFT") {
        if (b === openVal) {
          oris.push("horizontalflipped");
          openVal = a;
        } else if (a === openVal) {
          oris.push("horizontal");
          openVal = b;
        } else {
          oris.push("horizontal");
          openVal = a;
        }
      } else {
        if (a === openVal) {
          oris.push("horizontalflipped");
          openVal = b;
        } else if (b === openVal) {
          oris.push("horizontal");
          openVal = a;
        } else {
          oris.push("horizontal");
          openVal = b;
        }
      }
    });

    // peças da esquerda são renderizadas do extremo para o centro
    return side === "LEFT"
      ? {display: [...moves].reverse(), orientations: [...oris].reverse()}
      : {display: moves, orientations: oris};
  };

  const {
    leftDisplay,
    leftOrientations,
    middleOrientation,
    rightDisplay,
    rightOrientations,
  } = useMemo(() => {
    if (!middleMove) {
      return {
        leftDisplay: [],
        leftOrientations: [],
        middleOrientation: undefined,
        rightDisplay: [],
        rightOrientations: [],
      };
    }

    const midTile = middleMove.tilePlayed!;
    const [ma, mb] = tileValues(midTile);
    const middleOrientation: Orientation = isDoubleTile(midTile)
      ? "vertical"
      : "horizontal";

    const leftSim = simulateSide(leftChron, ma, "LEFT");
    const rightSim = simulateSide(rightChron, mb, "RIGHT");

    return {
      leftDisplay: leftSim.display,
      leftOrientations: leftSim.orientations,
      middleOrientation,
      rightDisplay: rightSim.display,
      rightOrientations: rightSim.orientations,
    };
  }, [middleMove, leftChron, rightChron]);

  const handleDrop = (
    e: React.DragEvent<HTMLDivElement>,
    moveDirection: MoveDirection
  ) => {
    e.preventDefault();
    const raw = e.dataTransfer.getData("tile");
    if (!raw) return;
    try {
      const {tile, index} = JSON.parse(raw);
      if (tile && typeof index === "number") {
        onTileDrop?.(tile, index, moveDirection);
      }
    } catch {
      // ignore
    }
  };

  return (
    <div
      className="bg-zinc-800 border border-zinc-600 rounded-2xl p-4 relative w-full h-80 flex items-center justify-center">
      {/* zona esquerda */}
      <div
        className="absolute left-0 top-0 w-1/2 h-full"
        onDragOver={(e) => e.preventDefault()}
        onDrop={(e) => handleDrop(e, "LEFT")}
      />

      {/* zona direita */}
      <div
        className="absolute right-0 top-0 w-1/2 h-full"
        onDragOver={(e) => e.preventDefault()}
        onDrop={(e) => handleDrop(e, "RIGHT")}
      />

      {movesChronological.length === 0 ? (
        <p className="text-sm text-zinc-400">Nenhuma peça jogada ainda.</p>
      ) : (
        <>
          {leftDisplay.map((move, i) => (
            <GameDominoTile
              key={`L-${move.id}-${i}`}
              tile={move.tilePlayed!}
              orientation={leftOrientations[i]}
            />
          ))}

          <GameDominoTile
            key={`M-${middleMove!.id}`}
            tile={middleMove!.tilePlayed!}
            orientation={middleOrientation!}
          />

          {rightDisplay.map((move, i) => (
            <GameDominoTile
              key={`R-${move.id}-${i}`}
              tile={move.tilePlayed!}
              orientation={rightOrientations[i]}
            />
          ))}
        </>
      )}
    </div>
  );
};

export default BoardCenterComponent;
