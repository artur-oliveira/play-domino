
const RecentGamesComponent = () => {
    const recentGames = [
        { id: 1, result: "Vitória", bet: 200, date: "07/05/2025" },
        { id: 2, result: "Derrota", bet: 500, date: "06/05/2025" },
        { id: 3, result: "Vitória", bet: 1000, date: "05/05/2025" },
    ];

    return (
        <ul className="space-y-2">
            {recentGames.map((game) => (
                <li key={game.id} className="bg-zinc-700 p-3 rounded flex justify-between">
                    <span>{game.result}</span>
                    <span>Aposta: {game.bet}</span>
                    <span>{game.date}</span>
                </li>
            ))}
        </ul>
    );
};

export default RecentGamesComponent;
