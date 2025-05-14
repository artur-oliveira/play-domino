import {motion, AnimatePresence} from "framer-motion";
import {FiInfo, FiLogIn} from "react-icons/fi";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import {DominoGameResponse, JoinGame} from "../../../models/game.models.ts";
import {useUser} from "../../../providers/user/useUser.tsx";
import {FC, useState} from "react";
import InputComponent from "../../generic/InputComponent.tsx";

type GameCardComponentProps = {
    game: DominoGameResponse;
    handleJoin: (join: JoinGame) => void;
    isJoining: boolean;
}

const GameCardComponent: FC<GameCardComponentProps> = ({game, handleJoin, isJoining}) => {
    const {displayCurrency, displayDateTime} = useUser();
    const [password, setPassword] = useState<string>('')
    const [openRulesId, setOpenRulesId] = useState<number | null>(null);
    const toggleRules = (id: number) => setOpenRulesId(openRulesId === id ? null : id);
    const isOpen = openRulesId === game.id;

    const displayPasswordValidationMessage = (): string | null => {
        if (password.length > 0 && !/^[a-zA-Z0-9]{4,16}$/.test(password)) {
            return 'A senha deve conter apenas letras e números (4-16 caracteres)';
        }
        return null;
    };

    const canJoin = (): boolean => {
        if (!game.requiresPassword) {
            return true;
        }
        return password.length > 0 && /^[a-zA-Z0-9]{4,16}$/.test(password);
    }

    return (
        <motion.div
            initial={{opacity: 0, y: 20}}
            animate={{opacity: 1, y: 0}}
            exit={{opacity: 0}}
            className="flex flex-col justify-between bg-zinc-700 p-4 rounded-xl shadow hover:shadow-lg transition-shadow duration-300 w-64"
        >
            <div>
                <div className="text-sm text-zinc-400">
                    Dono da sala: <span className="text-[#fdeccd]">{game.host.name}</span>
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

            {game.requiresPassword && <div className="mt-2">
                <InputComponent
                    type="text"
                    name="password"
                    className="p-2 bg-zinc-600 text-[#fdeccd] rounded-md w-full text-sm"
                    labelName="Senha da partida"
                    value={password}
                    labelClassName="text-xs text-zinc-300 mb-1"
                    maxLength={16}
                    error={displayPasswordValidationMessage()}
                    onChange={(e) => setPassword(e.target.value)}
                />
            </div>}

            <ButtonComponent
                onClick={() => handleJoin({
                    gameId: game.id,
                    password: password || null,
                })}
                disabled={isJoining || !canJoin()}
                ignoreGenericProps={true}
                loading={isJoining}
                labelLoading="Entrando"
                className="mt-4 py-1 px-3 rounded bg-[#fdeccd] text-zinc-900 text-sm font-medium flex items-center justify-center gap-1 hover:opacity-90 transition"
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
                                ? `ganhar ${game.roundsToWin} round${game.roundsToWin === 1 ? '' : 's'}!`
                                : `fizer ${game.pointsToWin} ponto${game.pointsToWin === 1 ? '' : 's'}!`}
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
