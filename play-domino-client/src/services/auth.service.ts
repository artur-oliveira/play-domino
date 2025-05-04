import {LoginPayload, LoginResponse, RegisterPayload} from "../models/auth.models.ts";
import {api} from "../lib/axios.ts";
import {AxiosResponse} from "axios";

export const registerUser = async (payload: RegisterPayload): Promise<AxiosResponse<null>> => {
    return api.post('/v1/auth/register', payload);
};

export const loginUser = async (payload: LoginPayload): Promise<AxiosResponse<LoginResponse>> => {
    return api.post('/v1/auth/token', payload);
};

export const verifyUser = async (token: string): Promise<AxiosResponse<null>> => {
    return api.post(`/v1/auth/verify/${token}`);
}