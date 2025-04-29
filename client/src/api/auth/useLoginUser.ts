import {useMutation} from '@tanstack/react-query';
import {LoginPayload} from "../../models/auth.models.ts";
import {loginUser} from "../../services/auth.service.ts";


export const useLoginUser = () => {
    return useMutation({
        mutationFn: (payload: LoginPayload) => loginUser(payload),
    });
}
