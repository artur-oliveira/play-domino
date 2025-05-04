import {useQuery} from "@tanstack/react-query";
import {getMe} from "../../services/user.service.ts";

export const useGetMe = () => {
    return useQuery({
        queryKey: ["user/me"],
        queryFn: getMe
    });
};

