import WalletBalanceComponent from "../../components/app/wallet/WalletBalanceComponent.tsx";
import WalletTransactionsComponent from "../../components/app/wallet/WalletTransactionsComponent.tsx";
import WalletActionsComponent from "../../components/app/wallet/WalletActionsComponent.tsx";
import WalletDepositModalComponent from "../../components/app/wallet/WalletDepositModalComponent.tsx";
import {useModal} from "../../providers/useModal.ts";
import {useGetCurrentWallet} from "../../api/wallet/useGetWallet.ts";
import {useWalletDeposit} from "../../api/wallet/useWalletDeposit.ts";
import {ErrorUtils} from "../../utils/errorUtils.ts";
import WalletWithdrawModalComponent from "../../components/app/wallet/WalletWithdrawModalComponent.tsx";
import {useWalletWithdraw} from "../../api/wallet/useWalletWithdraw.ts";

const UserWalletPage = () => {
    const wallet = useGetCurrentWallet();
    const depositModal = useModal();
    const withdrawModal = useModal();
    const depositMutation = useWalletDeposit();
    const withdrawMutation = useWalletWithdraw();

    const handleConfirmDeposit = (amountCents: number) => {
        depositMutation.mutate({
            amountCents
        }, {
            onSuccess: () => {
                depositModal.closeModal();
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
            },
            onError: (error: Error) => {
                ErrorUtils.displayAxiosError(error);
            }
        });
    }

    return (
        <div className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
            <div className="p-4">
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
                <WalletTransactionsComponent/>

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
        </div>
    );
};

export default UserWalletPage;
