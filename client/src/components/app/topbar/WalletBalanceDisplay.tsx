// src/components/WalletBalanceDisplay.tsx

import {Skeleton} from "../../generic/Skeleton.tsx";
import {WalletResponse} from "../../../models/wallet.models.ts";

const WalletBalanceDisplay = (
    {data, isLoading, isError}: { data: WalletResponse | undefined; isLoading: boolean; isError: boolean }
) => {
    if (isLoading) {
        return (
            <div className="flex items-center space-x-2">
                <span className="text-xl">Saldo:</span>
                <Skeleton className="w-24 h-6"/>
            </div>
        );
    }

    if (isError) {
        return (
            <div className="flex items-center space-x-2 text-red-500">
                <span className="text-xl">Saldo:</span>
                <span>Erro</span>
            </div>
        );
    }

    return (
        <div className="flex items-center space-x-2">
            <span className="text-xl">Saldo:</span>
            <span className="text-xl font-semibold">
                {data?.displayAvailableCents}
            </span>
        </div>
    );
};

export default WalletBalanceDisplay;
