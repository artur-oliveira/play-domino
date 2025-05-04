import NewGameModal from "../game/NewGameModal.tsx";
import {useGetCurrentWallet} from "../../../api/wallet/useGetWallet.ts";
import TopBarLeftSide from "./TopBarLeftSide.tsx";
import TopBarRightSide from "./TopBarRightSide.tsx";
import {useModal} from "../../../providers/useModal.ts";
import {useAuth} from "../../../providers/auth/useAuth.tsx";

export const TopBar = () => {
    const wallet = useGetCurrentWallet();
    const {isOpen, openModal, closeModal} = useModal();

    const handleConfirm = () => {
        closeModal();
    };

    const authenticated = useAuth().isAuthenticated();

    return (
        <header
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center flex justify-between items-center">
            {/* Lado esquerdo */}
            <TopBarLeftSide/>
            <TopBarRightSide setModalOpen={openModal} wallet={wallet}/>
            {authenticated && wallet.data && <NewGameModal
                isOpen={isOpen}
                onClose={() => closeModal()}
                onConfirm={handleConfirm}
                wallet={wallet.data}
            />}
        </header>
    );
};
