import {useQuery} from "@tanstack/react-query";
import {getWalletTransactions} from "../../services/wallet.service.ts";

export const useGetTransactions = () => {
    return useQuery({
        queryKey: ["wallet/transactions"],
        queryFn: getWalletTransactions,
    });
};

