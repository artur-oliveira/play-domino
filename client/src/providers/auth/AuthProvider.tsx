// src/context/AuthContext.tsx
import {useState, ReactNode} from "react";
import {AuthContext} from "./AuthContext.tsx";
import {getTokenSub, getValidTokenFromStorage} from "../../utils/tokenUtils.ts";

const TOKEN_NAME: string = 'authToken';


interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({children}: AuthProviderProps) => {
    const [token, setToken] = useState<string | null>(getValidTokenFromStorage(TOKEN_NAME));

    const login = (newToken: string) => {
        localStorage.setItem(TOKEN_NAME, newToken);
        setToken(newToken);
    };

    const logout = () => {
        localStorage.removeItem(TOKEN_NAME);
        setToken(null);
    };

    const userName = () => {
        return getTokenSub(TOKEN_NAME) ?? '';
    };

    return (
        <AuthContext.Provider value={{token, login, logout, userName}}>
            {children}
        </AuthContext.Provider>
    );
};

