import {useMutation} from "@tanstack/react-query";
import {walletDeposit} from "../../services/wallet.service.ts";
import {WalletAmountPayload} from "../../models/wallet.models.ts";

export const useWalletDeposit = () => {
    return useMutation({
        mutationFn: (deposit: WalletAmountPayload) => walletDeposit(deposit),
    });
}

