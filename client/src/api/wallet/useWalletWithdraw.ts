import {useMutation} from "@tanstack/react-query";
import {WalletAmountPayload} from "../../models/wallet.models.ts";
import {walletWithdraw} from "../../services/wallet.service.ts";

export const useWalletWithdraw = () => {
    return useMutation({
        mutationFn: (deposit: WalletAmountPayload) => walletWithdraw(deposit),
    });
}

