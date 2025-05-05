import NewGameModal from "../game/NewGameModal.tsx";
import TopBarLeftSide from "./TopBarLeftSide.tsx";
import TopBarRightSide from "./TopBarRightSide.tsx";
import {useModal} from "../../../providers/useModal.ts";
import {useAuth} from "../../../providers/auth/useAuth.tsx";
import {useGetCurrentWallet} from "../../../api/wallet.api.ts";
import {CreateNewGame} from "../../../models/game.models.ts";
import {useCreateNewGame, useGetOngoingGame} from "../../../api/game.api.ts";
import {ErrorUtils} from "../../../utils/errorUtils.ts";
import {useNavigate} from "react-router-dom";

export const TopBar = () => {
    const wallet = useGetCurrentWallet();
    const {isOpen, openModal, closeModal} = useModal();
    const getOngoingGame = useGetOngoingGame();
    const createNewGame = useCreateNewGame();
    const navigate = useNavigate();

    const handleConfirm = (game: CreateNewGame) => {
        createNewGame.mutate(game, {
            onSuccess: () => {
                navigate("/app/game");
                closeModal();
            },
            onError: (err) => {
                ErrorUtils.displayAxiosError(err)
            }
        })
    };
    const authenticated = useAuth().isAuthenticated();

    return (
        <header
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center flex justify-between items-center">
            {/* Lado esquerdo */}
            <TopBarLeftSide/>
            <TopBarRightSide setModalOpen={openModal} wallet={wallet} onGoingGame={getOngoingGame}/>
            {authenticated && wallet.data && <NewGameModal
                isOpen={isOpen}
                onClose={() => closeModal()}
                onConfirm={handleConfirm}
                wallet={wallet.data}
            />}
        </header>
    );
};
