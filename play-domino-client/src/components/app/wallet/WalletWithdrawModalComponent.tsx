import {FC} from "react";
import {WalletResponse} from "../../../models/wallet.models.ts";
import WalletAmountModalComponent from "./WalletAmountModalComponent.tsx";

interface WalletWithdrawModalProps {
    wallet: WalletResponse;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
}

const WalletWithdrawModalComponent: FC<WalletWithdrawModalProps> = ({wallet, isOpen, onClose, onConfirm}) => {
    const maxAmount = Math.min(wallet.availableCents, wallet.maximumWithdrawCents);

    return (
        <WalletAmountModalComponent
            title="Saque"
            label="Valor do saque"
            buttonLabel="Sacar"
            minValue={wallet.minimumWithdrawCents}
            maxValue={maxAmount}
            isOpen={isOpen}
            onClose={onClose}
            onConfirm={onConfirm}/>
    );
};

export default WalletWithdrawModalComponent;
