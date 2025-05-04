import {api} from "../lib/axios.ts";
import {CurrentUser, UserUpdate} from "../models/users.models.ts";

export const getMe = async (): Promise<CurrentUser> => {
    return api.get("/v1/user/me").then((response) => response.data);
};

export const updateMe = async (update: UserUpdate): Promise<CurrentUser> => {
    return api.put("/v1/user/me", update).then((response) => response.data);
};
