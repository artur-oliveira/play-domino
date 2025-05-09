import {motion, AnimatePresence} from "framer-motion";
import {FiInfo, FiLogIn} from "react-icons/fi";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import {DominoGameResponse} from "../../../models/game.models.ts";
import {useUser} from "../../../providers/user/useUser.tsx";
import {FC, useState} from "react";

type GameCardComponentProps = {
    game: DominoGameResponse;
    handleJoin: (gameId: number) => void;
    isJoining: boolean;
}

const GameCardComponent: FC<GameCardComponentProps> = ({game, handleJoin, isJoining}) => {
    const {displayCurrency, displayDateTime} = useUser();
    const [openRulesId, setOpenRulesId] = useState<number | null>(null);
    const toggleRules = (id: number) => setOpenRulesId(openRulesId === id ? null : id);
    const isOpen = openRulesId === game.id;

    return (
        <motion.div
            initial={{opacity: 0, y: 20}}
            animate={{opacity: 1, y: 0}}
            exit={{opacity: 0}}
            className="flex flex-col justify-between bg-zinc-700 p-4 rounded-xl shadow hover:shadow-lg transition-shadow duration-300 w-64"
        >
            <div className="mb-2">
                <div className="text-sm text-zinc-400">
                    Host: <span className="text-[#fdeccd]">{game.host.name}</span>
                </div>
                <div className="text-sm text-zinc-400">
                    Data: <span className="text-[#fdeccd]">{displayDateTime(game.createdAt)}</span>
                </div>
                <div className="text-sm text-zinc-400">
                    Aposta: <span className="text-[#fdeccd]">
                        {game.betAmountCents === 0 ? 'Sem aposta' : displayCurrency(game.betAmountCents)}
                    </span>
                </div>
                <div className="text-sm text-zinc-400">
                    Jogadores: <span className="text-[#fdeccd]">{game.players.length}/4</span>
                </div>
            </div>

            <ButtonComponent
                onClick={() => handleJoin(game.id)}
                disabled={isJoining}
                ignoreGenericProps={true}
                loading={isJoining}
                labelLoading="Entrando"
                className="mt-2 py-1 px-3 rounded bg-[#fdeccd] text-zinc-900 text-sm font-medium flex items-center justify-center gap-1 hover:opacity-90 transition"
                label={<><FiLogIn/> Entrar</>}
            />

            <ButtonComponent
                onClick={() => toggleRules(game.id)}
                label={<><FiInfo size={14}/> {isOpen ? "Ocultar regras" : "Ver regras"}</>}
                ignoreGenericProps={true}
                className="mt-2 text-xs text-[#fdeccd] underline flex items-center gap-1 hover:no-underline"
            >

            </ButtonComponent>

            <AnimatePresence>
                {isOpen && (
                    <motion.div
                        initial={{height: 0, opacity: 0}}
                        animate={{height: "auto", opacity: 1}}
                        exit={{height: 0, opacity: 0}}
                        style={{originY: 0}}
                        className="overflow-hidden mt-2 bg-zinc-800 p-2 rounded text-xs text-zinc-300 max-h-32"
                    >
                        <ul className="list-disc pl-4 space-y-1">
                            <li>
                                Vence quem {game.gameWinCondition === 'ROUNDS'
                                ? `ganhar ${game.roundsToWin} rounds!`
                                : `fizer ${game.pointsToWin} pontos!`}
                            </li>
                            <li>{game.allowCloseGame ? 'NÃO pode trancar o jogo' : 'Pode trancar o jogo'}</li>
                            <li>{game.allowBots ? 'Esta partida pode conter robôs' : 'Apenas jogadores reais (Sem robôs)'}</li>
                        </ul>
                    </motion.div>
                )}
            </AnimatePresence>
        </motion.div>
    );
};

export default GameCardComponent;
