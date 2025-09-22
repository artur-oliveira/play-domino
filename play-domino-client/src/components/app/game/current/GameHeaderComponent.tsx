import {AiOutlineCloseCircle} from "react-icons/ai";
import {FiPlay, FiShare2} from "react-icons/fi";
import {DominoGameResponse} from "../../../../models/game.models.ts";
import {FC} from "react";
import {useUser} from "../../../../providers/user/useUser.tsx";
import {IoMdExit} from "react-icons/io";
import {HiPlay} from "react-icons/hi";

type GameHeaderComponentProps = {
  game: DominoGameResponse;
  onCancel: () => void;
  onShare: () => void;
  onStart: () => void;
  onExit: () => void;
}
const GameHeaderComponent: FC<GameHeaderComponentProps> = ({game, onCancel, onShare, onStart, onExit}) => {
  const {displayCurrency, displayDateTime} = useUser();
  const displayGameStatus = () => {
    switch (game.status) {
      case "CANCELLED":
        return 'Cancelado';
      case "WAITING_FOR_PLAYERS":
        return 'Aguardando jogadores';
      case "FINISHED":
        return 'Finalizado';
      case "IN_PROGRESS":
        return 'Em andamento';
    }
  }

  const displayGameBetAmount = () => {
    return game.betAmountCents === 0 ? 'Sem aposta' : displayCurrency(game.betAmountCents);
  }

  return (
    <div className="flex justify-between items-start m-b-">
      {/* Lado Esquerdo: Informações */}
      <div>
        <h2 className="text-xl font-bold mb-2">Informações do Jogo</h2>
        <p>Status: <span className="font-semibold">{displayGameStatus()}</span></p>
        <p>Aposta: <span className="font-semibold">{displayGameBetAmount()}</span></p>
        <p>Dono da sala: <span className="font-semibold">{game.host.name}</span></p>
        <p>Data de criação: <span className="font-semibold">{displayDateTime(game.createdAt)}</span></p>
      </div>

      {/* Lado Direito: Ações */}
      <div className="flex flex-col gap-4">
        {game.currentHost && game.status === 'WAITING_FOR_PLAYERS' && <button
            onClick={onCancel}
            title="Cancelar Partida"
            className="text-zinc-400 hover:text-red-400 transition-colors"
        >
            <AiOutlineCloseCircle size={28}/>
        </button>}
        {game.currentHost && game.status === 'WAITING_FOR_PLAYERS' && game.inviteCode && <button
            onClick={onShare}
            title="Compartilhar"
            className="text-zinc-400 hover:text-yellow-400 transition-colors"
        >
            <FiShare2 size={26}/>
        </button>}
        {game.currentHost && game.status === 'WAITING_FOR_PLAYERS' && <button
            onClick={onStart}
            title="Iniciar partida"
            className="text-zinc-400 hover:text-green-400 transition-colors"
        >
            <HiPlay size={26}/>
        </button>}
        {!game.currentHost && game.status === 'WAITING_FOR_PLAYERS' && <button
            onClick={onExit}
            title="Sair da partida"
            className="text-zinc-400 hover:text-red-400 transition-colors"
        >
            <IoMdExit size={26}/>
        </button>}
      </div>
    </div>
  );
};

export default GameHeaderComponent;
