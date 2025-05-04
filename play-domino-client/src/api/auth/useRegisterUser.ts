import {useMutation} from '@tanstack/react-query';
import {RegisterPayload} from "../../models/auth.models.ts";
import {registerUser} from "../../services/auth.service.ts";


export const useRegisterUser = () => {
    return useMutation({
        mutationFn: (payload: RegisterPayload) => registerUser(payload),
    });
}
