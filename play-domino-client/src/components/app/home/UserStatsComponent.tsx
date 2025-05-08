
const UserStatsComponent = () => {
    const stats = {
        victories: 50,
        gamesPlayed: 120,
        balance: 3000,
    };

    return (
        <div className="grid grid-cols-3 gap-4 text-center">
            <div className="bg-zinc-700 p-4 rounded">
                <h4 className="text-lg font-semibold">Vitórias</h4>
                <p className="text-xl">{stats.victories}</p>
            </div>
            <div className="bg-zinc-700 p-4 rounded">
                <h4 className="text-lg font-semibold">Partidas</h4>
                <p className="text-xl">{stats.gamesPlayed}</p>
            </div>
            <div className="bg-zinc-700 p-4 rounded">
                <h4 className="text-lg font-semibold">Saldo</h4>
                <p className="text-xl">{stats.balance}</p>
            </div>
        </div>
    );
};

export default UserStatsComponent;
