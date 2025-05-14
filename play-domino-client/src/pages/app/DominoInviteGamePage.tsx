import {useParams} from "react-router-dom";
import DominoInviteGameComponent from "../../components/app/game/DominoInviteGameComponent.tsx";

const DominoInviteGamePage = () => {
    const {code} = useParams();
    return (
        <div
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center space-y-6">
            {!code && (
                <div className="rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center italic text-center">
                    Convite inválido ou não informado!
                </div>
            )}
            {code && <DominoInviteGameComponent inviteCode={code}/>}
        </div>
    );
}

export default DominoInviteGamePage;