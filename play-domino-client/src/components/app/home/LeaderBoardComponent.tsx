
const LeaderboardComponent = () => {
    const leaderboard = [
        { id: 1, name: "Jogador1", wins: 30, balance: 5000 },
        { id: 2, name: "Jogador2", wins: 25, balance: 4000 },
        { id: 3, name: "Jogador3", wins: 20, balance: 3500 },
    ];

    return (
        <ul className="space-y-2">
            {leaderboard.map((player) => (
                <li key={player.id} className="bg-zinc-700 p-3 rounded flex justify-between">
                    <span>{player.name}</span>
                    <span>Vitórias: {player.wins}</span>
                    <span>Saldo: {player.balance}</span>
                </li>
            ))}
        </ul>
    );
};

export default LeaderboardComponent;
