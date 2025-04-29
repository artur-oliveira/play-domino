import {useQuery} from "@tanstack/react-query";
import {getCurrentWallet} from "../../services/wallet.service.ts";

export const useGetCurrentWallet = () => {
    return useQuery({
        queryKey: ["wallet"],
        queryFn: getCurrentWallet,
    });
};

