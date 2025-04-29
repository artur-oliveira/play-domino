import {HiPlay} from "react-icons/hi";
import {Skeleton} from "../../generic/Skeleton.tsx";

type Props = {
    setModalOpen: (open: boolean) => void;
    isLoadingWallet: boolean;
}
const NewGameButton = ({setModalOpen, isLoadingWallet}: Props) => {
    if (isLoadingWallet) {
        return <Skeleton className="w-36 h-8"/>
    }
    return <button
        onClick={() => setModalOpen(true)}
        aria-label="Criar nova partida"
        className="bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 text-white px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105 flex items-center gap-2 "
    >
        <HiPlay/>
        Nova Partida
    </button>
}

export default NewGameButton;