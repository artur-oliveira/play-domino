import {useEffect} from "react";
import {useGetOngoingGame} from "../../../api/game.api.ts";
import {useNavigate} from "react-router-dom";

const CheckCurrentGameComponent = () => {
    const onGoingGame = useGetOngoingGame();
    const navigate = useNavigate();

    useEffect(() => {
        if (onGoingGame.isSuccess && onGoingGame.data !== null) {
            navigate("/app/game");
        }
    }, [onGoingGame, navigate])
    return <></>
}

export default CheckCurrentGameComponent;