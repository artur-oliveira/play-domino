import {useMutation, useQuery} from "@tanstack/react-query";
import {CancelGame, CreateMove, CreateNewGame, JoinGame} from "../models/game.models.ts";
import {
  cancelGame,
  createMove,
  startGame,
  createNewGame,
  exitGame,
  getInviteGame,
  getOngoingGame,
  getPublicGames,
  joinGame
} from "../services/game.service.ts";

export const useCreateNewGame = () => {
  return useMutation({
    mutationFn: (game: CreateNewGame) => createNewGame(game),
  });
};

export const useJoinGame = () => {
  return useMutation({
    mutationFn: (join: JoinGame) => joinGame(join),
  });
};

export const useCancelGame = () => {
  return useMutation({
    mutationFn: (cancel: CancelGame) => cancelGame(cancel),
  });
};

export const useCreateMove = () => {
  return useMutation({
    mutationFn: (createM: CreateMove) => createMove(createM),
  });
};

export const useStartGame = () => {
  return useMutation({
    mutationFn: (gameId: number) => startGame(gameId),
  });
};

export const useExitGame = () => {
  return useMutation({
    mutationFn: (gameId: number) => exitGame(gameId),
  });
};

export const useGetOngoingGame = () => {
  return useQuery({
    queryKey: ["game/ongoing"],
    queryFn: getOngoingGame
  });
};
export const useGetInviteGame = (inviteCode: string) => {
  return useQuery({
    queryKey: ["game/invite"],
    queryFn: () => getInviteGame(inviteCode)
  });
};

export const useGetPublicGames = () => {
  return useQuery({
    queryKey: ["game/public"],
    queryFn: getPublicGames
  });
};