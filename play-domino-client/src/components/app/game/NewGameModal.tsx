import React, {FC, useEffect, useState} from "react";
import {HiX, HiChevronDown, HiChevronUp} from "react-icons/hi";
import {WalletResponse} from "../../../models/wallet.models.ts";
import GenericModalComponent from "../../generic/GenericModalComponent.tsx";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import InputComponent from "../../generic/InputComponent.tsx";
import {useUser} from "../../../providers/user/useUser.tsx";
import {CreateNewGame, GameStartCondition, GameWinCondition} from "../../../models/game.models.ts";

interface NewGameModalProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (game: CreateNewGame) => void;
    wallet: WalletResponse;
}

const NewGameModal: FC<NewGameModalProps> = ({
                                                 isOpen, onClose, onConfirm, wallet
                                             }) => {
    const options = [0, 200, 500, 1000, 2000, -1];
    const [selected, setSelected] = useState<number>(0);
    const [amount, setAmount] = useState<string>("");
    const [isPublic, setIsPublic] = useState<boolean>(true);
    const [showAdvanced, setShowAdvanced] = useState<boolean>(false);
    const [password, setPassword] = useState<string>("");
    const [allowBots, setAllowBots] = useState<boolean>(false);
    const [gameStartCondition, setGameStartCondition] = useState<GameStartCondition>('LAST_WINNER');
    const [gameWinCondition, setGameWinCondition] = useState<GameWinCondition>('ROUNDS');
    const [pointsToWin, setPointsToWin] = useState<number>(10);
    const [roundsToWin, setRoundsToWin] = useState<number>(5);

    const {displayCurrency} = useUser();

    useEffect(() => {
        if (selected !== null && selected !== -1) {
            setAmount("");
        }
    }, [selected]);

    function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        const formatted = displayCurrency(value);
        setAmount(formatted);
    }

    function getRawSelectedAmount(): number {
        if (!amount) {
            return 0;
        }
        const cleaned = amount.replace(/\D+/g, '');
        return isNaN(parseInt(cleaned)) ? 0 : parseInt(cleaned);
    }

    const getBetAmount = () => {
        return selected === -1 ? getRawSelectedAmount() : selected;
    }

    const balance = wallet.availableCents;
    const maxAmount = Math.min(wallet.availableCents, wallet.maximumBetCents);
    const maxBalanceLength = displayCurrency(maxAmount.toString()).length;

    const isValidAmound = (): boolean => {
        const betAmount = selected === -1 ? getRawSelectedAmount() / 100.0 : selected;
        return betAmount === 0 || (betAmount >= wallet.minimumBetCents && betAmount <= balance && betAmount <= maxAmount);
    }

    const isValidPassword = (): boolean => {
        return password.length === 0 || /^[a-zA-Z0-9]{4,16}$/.test(password);
    }

    const displayBetAmountValidationMessage = (): string | null => {
        if (isValidAmound()) {
            return null;
        }
        const min = displayCurrency(wallet.minimumBetCents);
        const max = displayCurrency(maxAmount);
        return `Sua aposta mínima deve ser entre ${min} e ${max}`;
    };

    const displayPasswordValidationMessage = (): string | null => {
        if (password.length > 0 && !/^[a-zA-Z0-9]{4,16}$/.test(password)) {
            return 'A senha deve conter apenas letras e números (4-16 caracteres)';
        }
        return null;
    };

    const isFormValid = (): boolean => {
        return isValidAmound() && isValidPassword();
    }

    const handleConfirm = (): void => {
        onConfirm({
            betAmountCents: getBetAmount(),
            password: password || null,
            public: isPublic,
            allowBots: allowBots || false,
            gameWinCondition: gameWinCondition || "ROUNDS",
            gameStartCondition: gameStartCondition || "LAST_WINNER",
            pointsToWin: pointsToWin || 10,
            roundsToWin: roundsToWin || 5,
        });
    }

    return (
        <GenericModalComponent
            isOpen={isOpen}
            onClose={onClose}
            child={
                <>
                    <ButtonComponent
                        label={<HiX size={20}/>}
                        onClick={onClose}
                        className="absolute top-3 right-3 text-[#fdeccd] hover:text-red-400 transition bg-inherit"
                    />

                    <h2 className="text-xl font-bold mb-2">Nova Partida</h2>
                    <p className="text-sm mb-6">Saldo disponível: <strong>{wallet.displayAvailableCents}</strong></p>

                    <div className="grid grid-cols-2 gap-3 mb-4">
                        {options.map(value => (
                            <ButtonComponent
                                key={value}
                                disabled={balance < value || balance === 0}
                                onClick={() => setSelected(value)}
                                className={`py-2 px-3 rounded-lg border font-medium transition ${
                                    selected === value
                                        ? 'bg-[#fdeccd] text-zinc-900 border-[#fdeccd]'
                                        : 'bg-inherit text-inherit border-zinc-500 hover:bg-zinc-800'
                                }`}
                                label={value === 0 ? 'Sem aposta' : value === -1 ? 'Personalizado' : displayCurrency(value)}
                            />
                        ))}
                    </div>

                    {selected === -1 && (
                        <InputComponent
                            type="text"
                            name="amount"
                            className="mt-1 p-3 bg-zinc-700 text-[#fdeccd] rounded-md w-full"
                            labelName="Valor da aposta"
                            value={amount}
                            labelClassName="text-sm text-zinc-300"
                            maxLength={maxBalanceLength}
                            placeholder="Digite o valor"
                            inputMode="decimal"
                            error={displayBetAmountValidationMessage()}
                            onKeyDown={(e) => {
                                if (["e", "E", "+", "-"].includes(e.key)) {
                                    e.preventDefault();
                                }
                            }}
                            onChange={handleChange}
                        />
                    )}

                    {!isValidAmound() &&
                        <p className="text-red-500 text-sm mt-1">{}</p>}

                    {/* Público/Privado */}
                    <div className="flex items-center mt-6">
                        <input
                            id="isPublic"
                            type="checkbox"
                            checked={isPublic}
                            onChange={(e) => setIsPublic(e.target.checked)}
                            className="w-4 h-4 text-[#fdeccd] bg-zinc-700 border-zinc-600 rounded focus:ring-[#fdeccd]"
                        />
                        <label htmlFor="isPublic" className="ml-2 text-sm text-zinc-300">Partida pública</label>
                    </div>

                    {/* Opções avançadas */}
                    <div className="mt-4">
                        <ButtonComponent
                            type="button"
                            ignoreGenericProps={true}
                            className="flex items-center text-sm text-[#fdeccd] hover:underline"
                            onClick={() => setShowAdvanced(!showAdvanced)}
                            label={showAdvanced ? <><HiChevronUp className="mr-1"/> Ocultar opções adicionais</> : <>
                                <HiChevronDown className="mr-1"/> Mostrar opções adicionais</>}
                        >
                        </ButtonComponent>

                        {showAdvanced && (
                            <div className="mt-4 transition-all space-y-4">

                                <InputComponent
                                    type="text"
                                    name="password"
                                    className="p-3 bg-zinc-700 text-[#fdeccd] rounded-md w-full"
                                    labelName="Senha (opcional)"
                                    value={password}
                                    labelClassName="text-sm text-zinc-300"
                                    maxLength={16}
                                    error={displayPasswordValidationMessage()}
                                    placeholder="Senha para entrar na partida"
                                    onChange={(e) => setPassword(e.target.value)}
                                />

                                {/* allowBots */}
                                <div className="flex items-center">
                                    <input
                                        id="allowBots"
                                        type="checkbox"
                                        checked={allowBots}
                                        onChange={(e) => setAllowBots(e.target.checked)}
                                        className="w-4 h-4 text-[#fdeccd] bg-zinc-700 border-zinc-600 rounded focus:ring-[#fdeccd]"
                                    />
                                    <label htmlFor="allowBots" className="ml-2 text-sm text-zinc-300">
                                        Permitir entrada de bots
                                    </label>
                                </div>

                                {/* gameStartCondition */}
                                <div>
                                    <label className="text-sm text-zinc-300 mb-1 block">Quem começa a partida</label>
                                    <select
                                        value={gameStartCondition}
                                        onChange={(e) => setGameStartCondition(e.target.value as GameStartCondition)}
                                        className="w-full p-3 bg-zinc-700 text-[#fdeccd] rounded-md"
                                    >
                                        <option value="LAST_WINNER">Último vencedor</option>
                                        <option value="MAX_TILE">Maior peça na mão</option>
                                    </select>
                                    <p className="text-xs text-zinc-400 mt-1">Define quem faz a primeira jogada da
                                        partida.</p>
                                </div>

                                {/* gameWinCondition */}
                                <div>
                                    <label className="text-sm text-zinc-300 mb-1 block">Condição de vitória</label>
                                    <select
                                        value={gameWinCondition}
                                        onChange={(e) => setGameWinCondition(e.target.value as GameWinCondition)}
                                        className="w-full p-3 bg-zinc-700 text-[#fdeccd] rounded-md"
                                    >
                                        <option value="ROUNDS">Vitórias (rounds)</option>
                                        <option value="POINTS">Pontos</option>
                                    </select>
                                    <p className="text-xs text-zinc-400 mt-1">
                                        Escolha se o jogo termina por número de vitórias ou por pontos acumulados.
                                    </p>
                                </div>

                                {/* pointsToWin (visível apenas se POINTS) */}
                                {gameWinCondition === 'POINTS' && (
                                    <InputComponent
                                        type="number"
                                        name="pointsToWin"
                                        className="p-3 bg-zinc-700 text-[#fdeccd] rounded-md w-full"
                                        labelName="Pontos para vencer"
                                        value={pointsToWin}
                                        labelClassName="text-sm text-zinc-300"
                                        min={10}
                                        maxLength={3}
                                        placeholder="Ex: 100"
                                        onChange={(e) => setPointsToWin(parseInt(e.target.value) ?? 10)}
                                    />
                                )}

                                {/* roundsToWin (visível apenas se ROUNDS) */}
                                {gameWinCondition === 'ROUNDS' && (
                                    <InputComponent
                                        type="number"
                                        name="roundsToWin"
                                        className="p-3 bg-zinc-700 text-[#fdeccd] rounded-md w-full"
                                        labelName="Vitórias para vencer"
                                        value={roundsToWin}
                                        labelClassName="text-sm text-zinc-300"
                                        min={1}
                                        maxLength={1}
                                        placeholder="Ex: 3"
                                        onChange={(e) => setRoundsToWin(parseInt(e.target.value) ?? 1)}
                                    />
                                )}

                            </div>
                        )}
                    </div>

                    <div className="flex items-center justify-center gap-4 mt-6">
                        <ButtonComponent
                            label="Confirmar"
                            onClick={() => handleConfirm()}
                            disabled={!isFormValid()}
                            className="bg-[#fdeccd] text-zinc-900 py-2 px-4 rounded-lg font-semibold hover:opacity-90 transition disabled:opacity-40"
                        />
                    </div>
                </>
            }
        />
    );
}

export default NewGameModal;