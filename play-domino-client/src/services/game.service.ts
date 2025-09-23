import {api} from "../lib/axios.ts";
import {CancelGame, CreateMove, CreateNewGame, DominoGameResponse, JoinGame} from "../models/game.models.ts";
import {ListResponse} from "../models/generic.models.ts";

export const createNewGame = async (game: CreateNewGame): Promise<DominoGameResponse> => {
  return api.post('/v1/domino-game', game).then((res) => res.data);
}

export const cancelGame = async (cancel: CancelGame): Promise<DominoGameResponse> => {
  return api.post(`/v1/domino-game/${cancel.gameId}/cancel`, {approve: cancel.approve}).then((res) => res.data);
}

export const createMove = async (createMove: CreateMove): Promise<DominoGameResponse> => {
  return api.post(`/v1/domino-game/${createMove.gameId}/move`, {
    tile: createMove.tile,
    moveDirection: createMove.moveDirection,
  }).then((res) => res.data);
}

export const startGame = async (gameId: number): Promise<DominoGameResponse> => {
  return api.post(`/v1/domino-game/${gameId}/start`).then((res) => res.data);
}

export const exitGame = async (gameId: number): Promise<null> => {
  return api.post(`/v1/domino-game/${gameId}/exit`, {}).then((res) => res.data);
}

export const joinGame = async (join: JoinGame): Promise<DominoGameResponse> => {
  return api.post(`/v1/domino-game/${join.gameId}/join`, {password: join.password}).then((res) => res.data);
}

export const getOngoingGame = async (): Promise<DominoGameResponse | null> => {
  return api.get('/v1/domino-game/ongoing').then((res) => res.data || null)
}

export const getInviteGame = async (inviteCode: string): Promise<DominoGameResponse> => {
  return api.get(`/v1/domino-game/invite/${inviteCode}`).then((res) => res.data)
}

export const getPublicGames = async (): Promise<ListResponse<DominoGameResponse>> => {
  return api.get('/v1/domino-game/public').then((res) => res.data)
}