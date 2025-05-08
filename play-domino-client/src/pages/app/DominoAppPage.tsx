import CheckCurrentGameComponent from "../../components/app/game/CheckCurrentGameComponent.tsx";
import PublicGamesComponent from "../../components/app/home/PublicGamesComponent.tsx";
// import RecentGamesComponent from "../../components/app/home/RecentGamesComponent.tsx";
// import LeaderBoardComponent from "../../components/app/home/LeaderBoardComponent.tsx";
// import NewsBannerComponent from "../../components/app/home/NewsBannerComponent.tsx";
// import UserStatsComponent from "../../components/app/home/UserStatsComponent.tsx";

const DominoAppPage = () => {

    return (
        <div
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center space-y-6">
            {/* Checa jogo em andamento e redireciona */}
            <CheckCurrentGameComponent/>

            <PublicGamesComponent/>

            {/*/!* Histórico recente do usuário *!/*/}
            {/*<section>*/}
            {/*    <h2 className="text-xl font-bold mb-2 text-[#fdeccd]">Suas últimas partidas</h2>*/}
            {/*    <RecentGamesComponent/>*/}
            {/*</section>*/}

            {/*/!* Ranking dos melhores jogadores *!/*/}
            {/*<section>*/}
            {/*    <h2 className="text-xl font-bold mb-2 text-[#fdeccd]">Ranking semanal</h2>*/}
            {/*    <LeaderBoardComponent/>*/}
            {/*</section>*/}

            {/*/!* Notícias / atualizações do sistema *!/*/}
            {/*<section>*/}
            {/*    <NewsBannerComponent/>*/}
            {/*</section>*/}

            {/*/!* Estatísticas rápidas do usuário *!/*/}
            {/*<section>*/}
            {/*    <h2 className="text-xl font-bold mb-2 text-[#fdeccd]">Suas estatísticas</h2>*/}
            {/*    <UserStatsComponent/>*/}
            {/*</section>*/}
        </div>
    );
};

export default DominoAppPage;
