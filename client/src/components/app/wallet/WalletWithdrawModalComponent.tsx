import {FC} from "react";
import {WalletResponse} from "../../../models/wallet.models.ts";
import WalletAmountModalComponent from "./WalletAmountModalComponent.tsx";
import {formatCurrency} from "../../../utils/currencyUtils.ts";

interface WalletWithdrawModalProps {
    wallet: WalletResponse;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
}

const WalletWithdrawModalComponent: FC<WalletWithdrawModalProps> = ({wallet, isOpen, onClose, onConfirm}) => {
    const maxAmount = Math.min(wallet.availableCents, wallet.maximumWithdrawCents);

    const isValidAmount = (amount: number) => {
        return amount >= wallet.minimumWithdrawCents && amount <= maxAmount;
    };

    const displayValidationMessage = (amount: number) => {
        if (amount === null || amount === undefined) {
            return null;
        }
        return `O valor deve ser entre ${formatCurrency((wallet.minimumWithdrawCents).toString())} e ${formatCurrency((maxAmount).toString())}`
    }
    return (
        <WalletAmountModalComponent
            title="Saque"
            label="Valor do saque"
            buttonLabel="Sacar"
            isValidAmount={isValidAmount}
            displayValidationMessage={displayValidationMessage}
            isOpen={isOpen} onClose={onClose} onConfirm={onConfirm}/>
    );
};

export default WalletWithdrawModalComponent;
