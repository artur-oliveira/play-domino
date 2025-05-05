import React, {FC, useEffect, useState} from "react";
import {HiX, HiChevronDown, HiChevronUp} from "react-icons/hi";
import {WalletResponse} from "../../../models/wallet.models.ts";
import GenericModalComponent from "../../generic/GenericModalComponent.tsx";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import InputComponent from "../../generic/InputComponent.tsx";
import {useUser} from "../../../providers/user/useUser.tsx";
import {CreateNewGame} from "../../../models/game.models.ts";

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
            public: isPublic
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
                            <div className="mt-4 transition-all">
                                <InputComponent
                                    type="text"
                                    name="password"
                                    className="mt-1 p-3 bg-zinc-700 text-[#fdeccd] rounded-md w-full"
                                    labelName="Senha (opcional)"
                                    value={password}
                                    labelClassName="text-sm text-zinc-300"
                                    maxLength={16}
                                    error={displayPasswordValidationMessage()}
                                    placeholder="Senha para entrar na partida"
                                    onChange={(e) => setPassword(e.target.value)}
                                />
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