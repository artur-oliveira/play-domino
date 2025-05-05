import {HiPlay} from "react-icons/hi";
import {Skeleton} from "../../generic/Skeleton.tsx";
import {FC} from "react";
import {DominoGameResponse} from "../../../models/game.models.ts";
import ButtonComponent from "../../generic/ButtonComponent.tsx";

type NewGameButtonProps = {
    setModalOpen: (open: boolean) => void;
    onGoingGame?: DominoGameResponse | null;
    isLoading: boolean;
}
const NewGameButton: FC<NewGameButtonProps> = ({setModalOpen, onGoingGame, isLoading}) => {
    if (isLoading) {
        return <Skeleton className="w-36 h-8"/>
    }
    return <ButtonComponent
        onClick={() => setModalOpen(true)}
        aria-label="Criar nova partida"
        disabled={onGoingGame !== null}
        label={<><HiPlay/> Nova partida</>}
        className="bg-gradient-to-r from-green-500 to-green-600 hover:from-green-600 hover:to-green-700 text-[#fdeccd] px-6 py-3 rounded-xl shadow-md text-sm font-medium transition-all ease-in-out transform hover:scale-105 flex items-center gap-2 "
    >
    </ButtonComponent>
}

export default NewGameButton;