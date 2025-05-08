import {Skeleton} from "../../generic/Skeleton.tsx";
import {useGetPublicGames} from "../../../api/game.api.ts";
import ButtonComponent from "../../generic/ButtonComponent.tsx";
import {useState} from "react";
import {useUser} from "../../../providers/user/useUser.tsx";


const PublicGamesComponent = () => {
    const {data: games, isLoading, isError} = useGetPublicGames();
    const [openRulesId, setOpenRulesId] = useState<number | null>(null);
    const {displayCurrency, displayDateTime} = useUser();
    const toggleRules = (id: number) => {
        setOpenRulesId(openRulesId === id ? null : id);
    };
    const handleJoin = (gameId: number) => {
        console.log('TODO: Join game', gameId);
    }

    return (
        <div>
            <h2 className="text-lg font-semibold mb-4">Salas Públicas</h2>
            <div className="flex flex-wrap gap-4">
                {isLoading || isError || !games
                    ? Array.from({length: 6}).map((_, idx) => (
                        <Skeleton key={idx} className="h-24 w-64 rounded-xl"/>
                    ))
                    : games.data && games.data.length > 0
                        ? games.data.map((game) => (
                            <div
                                key={game.id}
                                className="flex flex-col justify-between bg-zinc-700 p-4 rounded-xl shadow hover:shadow-lg transition-shadow duration-300 w-64"
                            >
                                <div className="mb-2">
                                    <div className="text-sm text-zinc-400">
                                        Criador: <span className="text-[#fdeccd]">{game.host.name}</span>
                                    </div>
                                    <div className="text-sm text-zinc-400">
                                        Data: <span className="text-[#fdeccd]">{displayDateTime(game.createdAt)}</span>
                                    </div>
                                    <div className="text-sm text-zinc-400">
                                        Aposta: <span
                                        className="text-[#fdeccd]">{game.betAmountCents === 0 ? 'Sem aposta' : displayCurrency(game.betAmountCents)}
                                    </span>
                                    </div>
                                    <div className="text-sm text-zinc-400">
                                        Jogadores:{" "}
                                        <span className="text-[#fdeccd]">
                                          {game.players.length}/4
                                      </span>
                                    </div>
                                </div>
                                <ButtonComponent
                                    onClick={() => handleJoin(game.id)}
                                    ignoreGenericProps={true}
                                    className="mt-2 py-1 px-3 rounded bg-[#fdeccd] text-zinc-900 text-sm font-medium hover:opacity-90 transition"
                                    label="Entrar"
                                >
                                </ButtonComponent>
                                <ButtonComponent
                                    onClick={() => toggleRules(game.id)}
                                    ignoreGenericProps={true}
                                    className="mt-2 text-xs text-[#fdeccd] underline hover:no-underline"
                                    label={openRulesId === game.id ? "Ocultar regras" : "Ver regras"}
                                >
                                </ButtonComponent>

                                {openRulesId === game.id && (
                                    <div
                                        className="mt-2 bg-zinc-800 p-2 rounded text-xs text-zinc-300 max-h-32 overflow-y-auto">
                                        <ul className="list-disc pl-4 space-y-1">
                                            {/*TODO: Implement rules*/}
                                            <li>{game.betAmountCents}</li>
                                            <li>{game.betAmountCents}</li>
                                            <li>{game.betAmountCents}</li>
                                        </ul>
                                    </div>
                                )}
                            </div>
                        ))
                        : (
                            <div className="text-center text-zinc-400 w-full">
                                Nenhuma sala disponível no momento.
                            </div>
                        )}
            </div>
        </div>
    );
};

export default PublicGamesComponent;
