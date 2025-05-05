import {useMutation, useQuery} from "@tanstack/react-query";
import {CreateNewGame} from "../models/game.models.ts";
import {createNewGame, getOngoingGame} from "../services/game.service.ts";

export const useCreateNewGame = () => {
    return useMutation({
        mutationFn: (game: CreateNewGame) => createNewGame(game),
    });
};

export const useGetOngoingGame = () => {
    return useQuery({
        queryKey: ["game/ongoing"],
        queryFn: getOngoingGame
    });
};