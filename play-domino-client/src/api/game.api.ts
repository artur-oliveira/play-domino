import {useMutation, useQuery} from "@tanstack/react-query";
import {CancelGame, CreateNewGame} from "../models/game.models.ts";
import {cancelGame, createNewGame, getOngoingGame} from "../services/game.service.ts";

export const useCreateNewGame = () => {
    return useMutation({
        mutationFn: (game: CreateNewGame) => createNewGame(game),
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