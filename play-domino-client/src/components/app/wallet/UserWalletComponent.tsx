import WalletActionsComponent from "./WalletActionsComponent.tsx";
import WalletBalanceComponent from "./WalletBalanceComponent.tsx";
import WalletTransactionsComponent from "./WalletTransactionsComponent.tsx";
import WalletDepositModalComponent from "./WalletDepositModalComponent.tsx";
import WalletWithdrawModalComponent from "./WalletWithdrawModalComponent.tsx";
import {
    useGetCurrentWallet,
    useWalletDeposit,
    useWalletWithdraw
} from "../../../api/wallet.api.ts";
import {useModal} from "../../../providers/useModal.ts";
import {ErrorUtils} from "../../../utils/errorUtils.ts";
import {useUser} from "../../../providers/user/useUser.tsx";

const UserWalletComponent = () => {
    const wallet = useGetCurrentWallet();
    const depositModal = useModal();
    const withdrawModal = useModal();
    const depositMutation = useWalletDeposit();
    const withdrawMutation = useWalletWithdraw();
    const {user} = useUser();

    const refetchWallet = () => {
        void wallet.refetch();
    }

    const handleConfirmDeposit = (amountCents: number) => {
        depositMutation.mutate({
            amountCents
        }, {
            onSuccess: () => {
                depositModal.closeModal();
                refetchWallet();
            },
            onError: (error: Error) => {
                ErrorUtils.displayAxiosError(error);
            }
        });
    }

    const handleConfirmWithdraw = (amountCents: number) => {
        withdrawMutation.mutate({
            amountCents
        }, {
            onSuccess: () => {
                withdrawModal.closeModal();
                refetchWallet();
            },
            onError: (error: Error) => {
                ErrorUtils.displayAxiosError(error);
            }
        });
    }

    return <div className="p-4">
        <div className="flex justify-between items-center mb-4">
            <h1 className="text-2xl font-bold">Minha Carteira</h1>

            {/* BOTÕES DE AÇÃO */}
            <WalletActionsComponent
                isLoadingWallet={wallet.isLoading}
                wallet={wallet.data}
                isPendingDeposit={depositMutation.isPending}
                isPendingWithdraw={withdrawMutation.isPending}
                onClickDeposit={depositModal.openModal}
                onClickWithdraw={withdrawModal.openModal}/>
        </div>
        {/* SALDOS */}
        <WalletBalanceComponent/>
        {/* TRANSAÇÕES */}
        {user && <WalletTransactionsComponent user={user}/>}

        {wallet.data &&
            <WalletDepositModalComponent
                wallet={wallet.data}
                isOpen={depositModal.isOpen}
                onConfirm={handleConfirmDeposit}
                onClose={depositModal.closeModal}
            />}

        {wallet.data &&
            <WalletWithdrawModalComponent
                wallet={wallet.data}
                isOpen={withdrawModal.isOpen}
                onConfirm={handleConfirmWithdraw}
                onClose={withdrawModal.closeModal}
            />}
    </div>
}

export default UserWalletComponent;