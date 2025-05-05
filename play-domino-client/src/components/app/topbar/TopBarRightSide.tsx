import WalletBalanceDisplay from "./WalletBalanceDisplay.tsx";
import LogoutButton from "./LogoutButton.tsx";
import {UseQueryResult} from "@tanstack/react-query";
import {WalletResponse} from "../../../models/wallet.models.ts";
import NewGameButton from "./NewGameButton.tsx";
import {FC} from "react";
import {Link} from "react-router-dom";
import {DominoGameResponse} from "../../../models/game.models.ts";

type TopBarRightSideProps = {
    setModalOpen: (open: boolean) => void;
    wallet: UseQueryResult<WalletResponse, Error>;
    onGoingGame: UseQueryResult<DominoGameResponse | null, Error>;
}

const TopBarRightSide: FC<TopBarRightSideProps> = ({setModalOpen, wallet, onGoingGame}) => (
    <div className="flex items-center gap-4">
        <NewGameButton setModalOpen={setModalOpen} onGoingGame={onGoingGame.data} isLoading={wallet.isLoading || onGoingGame.isLoading}/>
        <Link to="/app/wallet">
            <WalletBalanceDisplay data={wallet.data} isLoading={wallet.isLoading} isError={wallet.isError}/>
        </Link>
        <LogoutButton/>
    </div>
);

export default TopBarRightSide;
