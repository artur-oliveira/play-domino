import {FC} from "react";
import {WalletResponse} from "../../../models/wallet.models.ts";
import WalletAmountModalComponent from "./WalletAmountModalComponent.tsx";

interface WalletDepositModalProps {
    wallet: WalletResponse;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (amount: number) => void;
}

const WalletDepositModalComponent: FC<WalletDepositModalProps> = ({wallet, isOpen, onClose, onConfirm}) => {

    return (
        <WalletAmountModalComponent
            title="Depósito"
            label="Valor do depósito"
            buttonLabel="Depositar"
            maxValue={wallet.maximumDepositCents}
            minValue={wallet.minimumDepositCents}
            isOpen={isOpen} onClose={onClose} onConfirm={onConfirm}/>
    );
};

export default WalletDepositModalComponent;
