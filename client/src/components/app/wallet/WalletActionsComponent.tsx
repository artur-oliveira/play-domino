import {FC} from "react";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import {Skeleton} from "../../generic/Skeleton.tsx";
import {WalletResponse} from "../../../models/wallet.models.ts";

type WalletActionsProps = {
    isLoadingWallet: boolean;
    wallet: WalletResponse | undefined;
    isPendingDeposit: boolean;
    isPendingWithdraw: boolean;
    onClickDeposit: (val: boolean) => void;
    onClickWithdraw: (val: boolean) => void;
}

const WalletActionsComponent: FC<WalletActionsProps> = ({
                                                            isLoadingWallet,
                                                            wallet,
                                                            isPendingDeposit,
                                                            isPendingWithdraw,
                                                            onClickDeposit,
                                                            onClickWithdraw,
                                                        }) => {

    return (
        <div className="flex gap-4">
            {isLoadingWallet ? <Skeleton className="h-11 w-28"/> : <ButtonComponent
                label="Depositar"
                loading={isPendingDeposit}
                disabled={wallet && wallet.pendingDepositCents > 0}
                onClick={() => onClickDeposit(true)}
                className="bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 text-white px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105"
            />}
            {isLoadingWallet ? <Skeleton className="h-11 w-28"/> : <ButtonComponent
                label="Sacar"
                disabled={wallet && wallet.pendingWithdrawCents > 0}
                loading={isPendingWithdraw}
                onClick={() => onClickWithdraw(true)}
                className="bg-gradient-to-r from-red-500 to-red-600 hover:from-red-600 hover:to-red-700 text-white px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105"
            />}
        </div>
    );
}

export default WalletActionsComponent
