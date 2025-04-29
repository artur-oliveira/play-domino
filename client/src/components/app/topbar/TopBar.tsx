import NewGameModal from "../game/NewGameModal.tsx";
import {useGetCurrentWallet} from "../../../api/wallet/useGetWallet.ts";
import TopBarLeftSide from "./TopBarLeftSide.tsx";
import TopBarRightSide from "./TopBarRightSide.tsx";
import {useModal} from "../../../providers/useModal.ts";

export const TopBar = () => {
    const wallet = useGetCurrentWallet();
    const {isOpen, openModal, closeModal} = useModal();

    const handleConfirm = () => {
        closeModal();
    };
    return (
        <header className="w-full bg-zinc-800 px-6 py-4 flex justify-between items-center border-b border-zinc-700">
            {/* Lado esquerdo */}
            <TopBarLeftSide/>
            <TopBarRightSide setModalOpen={openModal} wallet={wallet}/>
            <NewGameModal
                isOpen={isOpen}
                onClose={() => closeModal()}
                onConfirm={handleConfirm}
                wallet={wallet}
            />
        </header>
    );
};
