import {AiOutlineCloseCircle} from "react-icons/ai";
import {FiShare2} from "react-icons/fi";
import {DominoGameResponse} from "../../../../models/game.models.ts";
import {FC} from "react";
import {useUser} from "../../../../providers/user/useUser.tsx";

type GameHeaderComponentProps = {
    game: DominoGameResponse;
    onCancel: () => void;
    onShare: () => void;
}
const GameHeaderComponent: FC<GameHeaderComponentProps> = ({game, onCancel, onShare}) => {
    const {displayCurrency, displayDateTime} = useUser();
    const displayGameStatus = () => {
        switch (game.status) {
            case "CANCELED":
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
                <p>Criado por: <span className="font-semibold">{game.host.name}</span></p>
                <p>Data de criação: <span className="font-semibold">{displayDateTime(game.createdAt)}</span></p>
            </div>

            {/* Lado Direito: Ações */}
            <div className="flex flex-col gap-4">
                <button
                    onClick={onCancel}
                    title="Cancelar Partida"
                    className="text-zinc-400 hover:text-red-400 transition-colors"
                >
                    <AiOutlineCloseCircle size={28}/>
                </button>
                <button
                    onClick={onShare}
                    title="Compartilhar"
                    className="text-zinc-400 hover:text-yellow-400 transition-colors"
                >
                    <FiShare2 size={26}/>
                </button>
            </div>
        </div>
    );
};

export default GameHeaderComponent;
