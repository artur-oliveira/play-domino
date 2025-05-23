import {User} from "./users.models.ts";

export interface CreateNewGame {
    betAmountCents: number | null;
    password: string | null;
    public: boolean;
    allowBots: boolean;
    gameStartCondition: GameStartCondition;
    gameWinCondition: GameWinCondition;
    pointsToWin: number;
    roundsToWin: number;
}

export interface JoinGame {
    password: string | null;
    gameId: number;
}

export interface CancelGame {
    approve: boolean;
    gameId: number;
}

export type GameStartCondition = 'LAST_WINNER' | 'MAX_TILE';
export type GameWinCondition = 'ROUNDS' | 'POINTS';
export type VoteType = 'CANCEL_GAME';
export type GameStatus = 'WAITING_FOR_PLAYERS' | 'IN_PROGRESS' | 'FINISHED' | 'CANCELLED';
export type MoveDirection = 'RIGHT' | 'LEFT';
export type DominoTile = (
    'ZERO_ZERO'
    | 'ZERO_ONE'
    | 'ZERO_TWO'
    | 'ZERO_THREE'
    | 'ZERO_FOUR'
    | 'ZERO_FIVE'
    | 'ZERO_SIX'
    | 'ONE_ONE'
    | 'ONE_TWO'
    | 'ONE_THREE'
    | 'ONE_FOUR'
    | 'ONE_FIVE'
    | 'ONE_SIX'
    | 'TWO_TWO'
    | 'TWO_THREE'
    | 'TWO_FOUR'
    | 'TWO_FIVE'
    | 'TWO_SIX'
    | 'THREE_THREE'
    | 'THREE_FOUR'
    | 'THREE_FIVE'
    | 'THREE_SIX'
    | 'FOUR_FOUR'
    | 'FOUR_FIVE'
    | 'FOUR_SIX'
    | 'FIVE_FIVE'
    | 'FIVE_SIX'
    | 'SIX_SIX'
    )

export interface DominoGamePlayer {
    id: number;
    user: User;
    passedLastTurn: boolean;
    host: boolean;
    currentTurn: boolean;
    currentUser: boolean;
    hand: DominoTile[];
}

export interface DominoGameRound {
    id: number;
    moves: DominoGameMove[];
    pile: DominoTile[];
    lastLeftTile: DominoTile;
    lastRightTile: DominoTile;
    roundPoints: number;
    winner: DominoGamePlayer | null;
    passCount: number;
    isClosed: boolean;
    createdAt: string; // ZonedDateTime → ISO string
    startedAt: string | null;
    endedAt: string | null;
}

export interface DominoGameMove {
    id: number;
    player: DominoGamePlayer;
    tilePlayed: DominoTile;
    turn: number
    moveDirection: MoveDirection;
    passed: number;
    closedGame: number;
    createdAt: string;
}

export interface DominoGameVote {
    id: number;
    player: DominoGamePlayer;
    voteType: VoteType;
    approved: boolean;
}

export interface DominoGameResponse {
    id: number;
    status: GameStatus;
    host: User;
    players: DominoGamePlayer[];
    rounds: DominoGameRound[];
    votes: DominoGameVote[];
    currentPlayer: number;
    betAmountCents: number;
    gameStartCondition: GameStartCondition;
    gameWinCondition: GameWinCondition;
    pointsToWin: number;
    roundsToWin: number;
    allowCloseGame: boolean;
    requiresPassword: boolean;
    winner: DominoGamePlayer | null;
    visible: boolean;
    allowBots: boolean;
    currentHost: boolean;
    inviteCode: string | null;
    password: string;
    createdAt: string; // ZonedDateTime → ISO string
    startedAt: string | null;
    endedAt: string | null;
}
