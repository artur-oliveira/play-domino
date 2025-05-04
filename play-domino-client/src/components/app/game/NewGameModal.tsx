import React, {useEffect, useState} from "react";
import {HiX} from "react-icons/hi";
import {WalletResponse} from "../../../models/wallet.models.ts";
import GenericModalComponent from "../../generic/GenericModalComponent.tsx";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import InputComponent from "../../generic/InputComponent.tsx";
import {useUser} from "../../../providers/user/useUser.tsx";

interface Props {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
    wallet: WalletResponse;
}

export default function NewGameModal({isOpen, onClose, onConfirm, wallet}: Props) {
    const options = [0, 200, 500, 1000, 2000, -1];
    const [selected, setSelected] = useState<number>(0);
    const [amount, setAmount] = useState<string>("");
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

    const getBetAmount = () => {
        return selected === -1 ? getRawSelectedAmount() : selected;
    }

    function getRawSelectedAmount(): number {
        if (!amount) {
            return 0;
        }
        const cleaned = amount.replace(/\D+/g, '');
        return isNaN(parseInt(cleaned)) ? 0 : parseInt(cleaned);
    }

    const balance = wallet.availableCents;
    const maxAmount = Math.min(wallet.availableCents, wallet.maximumBetCents);
    const maxBalanceLength = displayCurrency(maxAmount.toString()).length;

    const isValid = (): boolean => {
        const betAmount = selected === -1 ? getRawSelectedAmount() / 100.0 : selected;
        return betAmount === 0 || (betAmount >= wallet.minimumBetCents && betAmount <= balance && betAmount <= maxAmount);
    }

    const displayBetAmountValidationMessage = (): string => {
        const min = displayCurrency(wallet.minimumBetCents);
        const max = displayCurrency(maxAmount);
        return `O valor da aposta deve ser entre ${min} e ${max}`;
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
                    >
                    </ButtonComponent>

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
                            onKeyDown={(e) => {
                                if (["e", "E", "+", "-"].includes(e.key)) {
                                    e.preventDefault();
                                }
                            }}
                            onChange={handleChange}
                        />
                    )}

                    {!isValid() && <p className="text-red-500 text-sm">{displayBetAmountValidationMessage()}
                    </p>}

                    <div className="flex items-center justify-center gap-4 mt-6">
                        <ButtonComponent
                            label="Confirmar"
                            onClick={() => onConfirm(getBetAmount())}
                            disabled={!isValid()}
                            className="bg-[#fdeccd] text-zinc-900 py-2 px-4 rounded-lg font-semibold hover:opacity-90 transition disabled:opacity-40"
                        />
                    </div>
                </>
            }/>
    );
}
