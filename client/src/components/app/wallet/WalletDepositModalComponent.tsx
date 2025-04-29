import {FC} from "react";
import {WalletResponse} from "../../../models/wallet.models.ts";
import WalletAmountModalComponent from "./WalletAmountModalComponent.tsx";
import {formatCurrency} from "../../../utils/currencyUtils.ts";

interface WalletDepositModalProps {
    wallet: WalletResponse;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
}

const WalletDepositModalComponent: FC<WalletDepositModalProps> = ({wallet, isOpen, onClose, onConfirm}) => {

    const isValidAmount = (amount: number) => {
        return amount >= wallet.minimumDepositCents && amount <= wallet.maximumDepositCents;
    };

    const displayValidationMessage = (amount: number) => {
        if (amount === null || amount === undefined) {
            return null;
        }
        return `O valor deve ser entre ${formatCurrency((wallet.minimumDepositCents).toString())} e ${formatCurrency((wallet.maximumDepositCents).toString())}`
    }
    return (
        <WalletAmountModalComponent
            title="Depósito"
            label="Valor do depósito"
            buttonLabel="Depositar"
            isValidAmount={isValidAmount}
            displayValidationMessage={displayValidationMessage}
            isOpen={isOpen} onClose={onClose} onConfirm={onConfirm}/>
    );
};

export default WalletDepositModalComponent;
