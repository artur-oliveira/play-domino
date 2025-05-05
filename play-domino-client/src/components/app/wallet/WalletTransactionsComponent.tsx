import {Skeleton} from "../../generic/Skeleton.tsx";
import {WalletTransactionResponse} from "../../../models/wallet.models.ts";
import {useWebSocketContext} from "../../../providers/websocket/useWebSocket.tsx";
import {CurrentUser} from "../../../models/users.models.ts";
import {FC, useEffect} from "react";
import {toast} from "sonner";
import {useGetTransactions} from "../../../api/wallet.api.ts";

type WalletTransactionsComponentProps = {
    user: CurrentUser;
}

const WalletTransactionsComponent: FC<WalletTransactionsComponentProps> = ({
                                                                               user,
                                                                           }) => {
    const {data, isLoading, isError, refetch} = useGetTransactions();
    const topic = `/topic/finance.${user.id}`;
    const {subscribeToTopic, unsubscribeFromTopic, messages} = useWebSocketContext();

    useEffect(() => {
        subscribeToTopic(topic);

        return () => {
            unsubscribeFromTopic(topic);
        }
    }, [topic, subscribeToTopic, unsubscribeFromTopic]);


    useEffect(() => {
        const financialMessages = messages[topic] || [];
        if (financialMessages.length > 0) {
            const lastMessage = financialMessages[financialMessages.length - 1];
            toast(`${lastMessage.message}`);
            void refetch();
        }
    }, [messages, topic, refetch]);

    const transactions: WalletTransactionResponse[] = data?.data ?? [];

    return (
        <div className="mt-8">
            <h2 className="text-lg font-bold text-[#fdeccd] mb-2">Histórico de Transações</h2>

            {isLoading || isError || !data ? (
                <div className="space-y-3">
                    <Skeleton className="w-full h-16"/>
                </div>
            ) : transactions.length === 0 ? (
                <div className="bg-zinc-800 border border-zinc-700 p-4 rounded-lg text-sm text-zinc-400 italic">
                    Nenhuma transação encontrada.
                </div>
            ) : (
                <div className="space-y-3">
                    {transactions.map((tx) => (
                        <div
                            key={tx.id}
                            className="bg-zinc-700 p-3 rounded-xl flex justify-between items-center shadow"
                        >
                            <div>
                                <div className="text-sm text-zinc-300">{tx.description}</div>
                                <div className="text-xs text-zinc-400">
                                    {new Date(tx.createdAt).toLocaleString()}
                                </div>
                                <div className="text-xs text-zinc-400">
                                    {tx.createdByName}
                                </div>
                            </div>
                            <div
                                className={`text-sm font-semibold ${
                                    tx.incoming ? 'text-green-400' : 'text-red-400'
                                }`}
                            >
                                {tx.incoming ? '+' : '-'}{tx.displayAmountCents}
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    )
}

export default WalletTransactionsComponent
