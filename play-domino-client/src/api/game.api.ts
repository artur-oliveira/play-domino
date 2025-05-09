import {useMutation, useQuery} from "@tanstack/react-query";
import {CancelGame, CreateNewGame} from "../models/game.models.ts";
import {cancelGame, createNewGame, getOngoingGame, getPublicGames, joinGame} from "../services/game.service.ts";

export const useCreateNewGame = () => {
    return useMutation({
        mutationFn: (game: CreateNewGame) => createNewGame(game),
    });
};

export const useJoinGame = () => {
    return useMutation({
        mutationFn: (gameId: number) => joinGame(gameId),
    });
};

export const useCancelGame = () => {
    return useMutation({
        mutationFn: (cancel: CancelGame) => cancelGame(cancel),
    });
};

export const useGetOngoingGame = () => {
    return useQuery({
        queryKey: ["game/ongoing"],
        queryFn: getOngoingGame
    });
};

export const useGetPublicGames = () => {
    return useQuery({
        queryKey: ["game/public"],
        queryFn: getPublicGames
    });
};