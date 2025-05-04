import React, {FC, useState} from "react";
import GenericModalComponent from "../../generic/GenericModalComponent.tsx";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import InputComponent from "../../generic/InputComponent.tsx";
import {useUser} from "../../../providers/user/useUser.tsx";

interface WalletAmountModal {
    title: string;
    label: string;
    buttonLabel: string;
    minValue: number;
    maxValue: number;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
}

const WalletAmoutModalComponent: FC<WalletAmountModal> = ({
                                                              title,
                                                              label,
                                                              buttonLabel,
                                                              isOpen,
                                                              minValue,
                                                              maxValue,
                                                              onClose,
                                                              onConfirm,
                                                          }) => {
    const [amount, setAmount] = useState<string>("");
    const {displayCurrency} = useUser();
    const maxValueLength = displayCurrency(maxValue.toString()).length;

    const isValidAmount = () => {
        return getRawAmount() >= minValue && getRawAmount() <= maxValue;
    };

    const displayValidationMessage = () => {
        if (amount === '' || getRawAmount() === null || getRawAmount() === undefined || maxValue === 0) {
            return null;
        }
        return `O valor deve ser entre ${displayCurrency((minValue).toString())} e ${displayCurrency(maxValue.toString())}`
    }

    function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        const formatted = displayCurrency(value);
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
                    <h2 className="text-xl font-bold text-[#fdeccd] mb-4">{title}</h2>

                    <div className="space-y-4">
                        <div className="flex flex-col">
                            <InputComponent
                                type="text"
                                name="amount"
                                className="mt-1 p-3 bg-zinc-700 text-[#fdeccd] rounded-md w-full"
                                value={amount}
                                labelName={label}
                                labelClassName="text-sm text-zinc-300"
                                maxLength={maxValueLength}
                                placeholder="Digite o valor"
                                inputMode="decimal"
                                onKeyDown={(e) => {
                                    if (["e", "E", "+", "-"].includes(e.key)) {
                                        e.preventDefault();
                                    }
                                }}
                                onChange={handleChange}
                            />
                        </div>
                        {!isValidAmount() && displayValidationMessage() &&
                            <p className="text-red-500 text-sm">{displayValidationMessage()}
                            </p>}

                        <div className="flex items-center justify-center gap-4 mt-6">
                            <ButtonComponent
                                label="Cancelar"
                                onClick={onClose}
                                className="bg-gradient-to-r from-zinc-500 to-zinc-600 hover:from-zinc-600 hover:to-zinc-700 text-[#fdeccd] px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105"
                            />
                            <ButtonComponent
                                label={buttonLabel}
                                disabled={!isValidAmount()}
                                onClick={() => onConfirm(getRawAmount())}
                                bgColor="bg-green-600"
                                className="text-[#fdeccd] px-6 py-3  rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105"
                            />
                        </div>
                    </div>
                </>
            }
        />
    );
};

export default WalletAmoutModalComponent;
