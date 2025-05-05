import {api} from "../lib/axios.ts";
import {CreateNewGame, DominoGameResponse} from "../models/game.models.ts";

export const createNewGame = async (game: CreateNewGame): Promise<DominoGameResponse> => {
    return api.post('/v1/domino-game', game).then((res) => res.data);
}

export const getOngoingGame = async (): Promise<DominoGameResponse | null> => {
    return api.get('/v1/domino-game/ongoing').then((res) => res.data || null)
}