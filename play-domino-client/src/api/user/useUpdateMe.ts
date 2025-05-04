import {useMutation} from "@tanstack/react-query";
import {UserUpdate} from "../../models/users.models.ts";
import {updateMe} from "../../services/user.service.ts";

export const useUpdateMe = () => {
    return useMutation({
        mutationFn: (payload: UserUpdate) => updateMe(payload),
    });
}

