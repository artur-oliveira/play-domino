import {Skeleton} from "../../generic/Skeleton.tsx";
import {useGetCurrentWallet} from "../../../api/wallet/useGetWallet.ts";


const WalletBalanceComponent = () => {

    const {data, isLoading, isError} = useGetCurrentWallet();
    return (
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
            <div className="bg-zinc-900 p-4 rounded-xl shadow">
                <div className="text-zinc-300 text-sm">Disponível</div>
                {isLoading || isError || !data ? (<Skeleton className="w-36 h-8"/>) :
                    (<div className="text-xl font-semibold">{data.displayAvailableCents}</div>)}
            </div>
            <div className="bg-zinc-900 p-4 rounded-xl shadow">
                <div className="text-zinc-300 text-sm">Bloqueado</div>
                {isLoading || isError || !data ? (<Skeleton className="w-36 h-8"/>) :
                    (<div className="text-xl font-semibold">{data.displayLockedCents}</div>)}
            </div>
            <div className="bg-zinc-900 p-4 rounded-xl shadow">
                <div className="text-zinc-300 text-sm">Depósitos Pendentes</div>
                {isLoading || isError || !data ? (<Skeleton className="w-36 h-8"/>) :
                    (<div className="text-xl font-semibold">{data.displayPendingDepositCents}</div>)}
            </div>
            <div className="bg-zinc-900 p-4 rounded-xl shadow">
                <div className="text-zinc-300 text-sm">Saques Pendentes</div>
                {isLoading || isError || !data ? (<Skeleton className="w-36 h-8"/>) :
                    (<div className="text-xl font-semibold">{data.displayPendingWithdrawCents}</div>)}
            </div>
        </div>
    );
}

export default WalletBalanceComponent
