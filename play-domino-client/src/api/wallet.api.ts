import {useMutation, useQuery} from "@tanstack/react-query";
import {getCurrentWallet, getWalletTransactions, walletDeposit, walletWithdraw} from "../services/wallet.service.ts";
import {WalletAmountPayload} from "../models/wallet.models.ts";

export const useGetTransactions = () => {
    return useQuery({
        queryKey: ["wallet/transactions"],
        queryFn: getWalletTransactions,
    });
};

export const useGetCurrentWallet = () => {
    return useQuery({
        queryKey: ["wallet"],
        queryFn: getCurrentWallet,
    });
};

export const useWalletDeposit = () => {
    return useMutation({
        mutationFn: (deposit: WalletAmountPayload) => walletDeposit(deposit),
    });
};

export const useWalletWithdraw = () => {
    return useMutation({
        mutationFn: (deposit: WalletAmountPayload) => walletWithdraw(deposit),
    });
};

