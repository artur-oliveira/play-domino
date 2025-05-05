import {useMutation} from "@tanstack/react-query";
import {loginUser, registerUser, verifyUser} from "../services/auth.service.ts";
import {LoginPayload, RegisterPayload} from "../models/auth.models.ts";

export const useConfirmCode = () => {
    return useMutation({
        mutationFn: (token: string) => verifyUser(token),
    });
};

export const useLoginUser = () => {
    return useMutation({
        mutationFn: (payload: LoginPayload) => loginUser(payload),
    });
};

export const useRegisterUser = () => {
    return useMutation({
        mutationFn: (payload: RegisterPayload) => registerUser(payload),
    });
};
