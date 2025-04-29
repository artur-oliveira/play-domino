import {createContext} from "react";

type AuthContextType = {
    token: string | null;
    userName: () => string;
    login: (token: string) => void;
    logout: () => void;
};

export const AuthContext = createContext<AuthContextType | undefined>(undefined);
