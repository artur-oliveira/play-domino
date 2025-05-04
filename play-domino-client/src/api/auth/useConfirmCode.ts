import {useMutation} from "@tanstack/react-query";
import {verifyUser} from "../../services/auth.service.ts";

export const useConfirmCode = () => {
    return useMutation({
        mutationFn: (token: string) => verifyUser(token),
    });
}

