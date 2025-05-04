import {api} from "../lib/axios.ts";
import {ListResponse} from "../models/generic.models.ts";
import {WalletAmountPayload, WalletResponse, WalletTransactionResponse} from "../models/wallet.models.ts";
import {AxiosResponse} from "axios";

export const getCurrentWallet = async (): Promise<WalletResponse> => {
    return api.get("/v1/wallet").then((response) => response.data);
};

export const getWalletTransactions = async (): Promise<ListResponse<WalletTransactionResponse>> => {
    return api.get("/v1/wallet/transactions").then((response) => response.data);
};

export const walletDeposit = async (payload: WalletAmountPayload): Promise<AxiosResponse<null>> => {
    return api.post('/v1/wallet/deposit', payload);
};

export const walletWithdraw = async (payload: WalletAmountPayload): Promise<AxiosResponse<null>> => {
    return api.post('/v1/wallet/withdraw', payload);
};
