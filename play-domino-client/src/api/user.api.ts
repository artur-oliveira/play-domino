import {useMutation, useQuery} from "@tanstack/react-query";
import {getMe, updateMe} from "../services/user.service.ts";
import {UserUpdate} from "../models/users.models.ts";

export const useGetMe = () => {
    return useQuery({
        queryKey: ["user/me"],
        queryFn: getMe
    });
};

export const useUpdateMe = () => {
    return useMutation({
        mutationFn: (payload: UserUpdate) => updateMe(payload),
    });
};

