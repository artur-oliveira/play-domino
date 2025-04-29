import React, {FC, useState} from "react";
import GenericModalComponent from "../../generic/GenericModalComponent.tsx";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import {formatCurrency} from "../../../utils/currencyUtils.ts";

interface WalletAmountModal {
    title: string;
    label: string;
    buttonLabel: string;
    isValidAmount: (amount: number) => boolean;
    displayValidationMessage: (amount: number) => string | null;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
}

const WalletAmoutModalComponent: FC<WalletAmountModal> = ({
                                                              title,
                                                              label,
                                                              buttonLabel,
                                                              isOpen,
                                                              isValidAmount,
                                                              displayValidationMessage,
                                                              onClose,
                                                              onConfirm
                                                          }) => {
    const [amount, setAmount] = useState<string>("");


    function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        const formatted = formatCurrency(value);
        setAmount(formatted);
    }

    function getRawAmount(): number {
        if (!amount) {
            return 0;
        }
        const cleaned = amount.replace(/\D+/g, '');
        return isNaN(parseInt(cleaned)) ? 0 : parseInt(cleaned);
    }


    return (
        <GenericModalComponent
            onClose={onClose}
            isOpen={isOpen}
            child={
                <>
                    <h2 className="text-xl font-bold text-white mb-4">{title}</h2>

                    <div className="space-y-4">
                        <div className="flex flex-col">
                            <label className="text-sm text-zinc-300">{label}</label>
                            <input
                                type="text"
                                value={amount}
                                onChange={handleChange}
                                placeholder="Digite o valor"
                                className="mt-1 p-3 bg-zinc-700 text-white rounded-xl"
                                inputMode="decimal"
                                onKeyDown={(e) => {
                                    if (["e", "E", "+", "-"].includes(e.key)) {
                                        e.preventDefault();
                                    }
                                }}
                            />
                        </div>
                        {!isValidAmount(getRawAmount()) && displayValidationMessage(getRawAmount()) &&
                            <p className="text-red-500 text-sm">{displayValidationMessage(getRawAmount())}
                            </p>}

                        <div className="flex gap-4 mt-6">
                            <ButtonComponent
                                label="Cancelar"
                                onClick={onClose}
                                className="bg-gradient-to-r from-zinc-500 to-zinc-600 hover:from-zinc-600 hover:to-zinc-700 text-white px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105"
                            />
                            <ButtonComponent
                                label={buttonLabel}
                                disabled={!isValidAmount(getRawAmount())}
                                onClick={() => onConfirm(getRawAmount())}
                                className="bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 text-white px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105"
                            />
                        </div>
                    </div>
                </>
            }
        />
    );
};

export default WalletAmoutModalComponent;
