import React from "react";
import {Navigate} from "react-router-dom";
import {useAuth} from "../providers/auth/useAuth.tsx";

export function ProtectedRoute({element}: { element: React.ReactNode }) {
    const {token} = useAuth();

    if (!token) {
        return <Navigate to="/auth/login"/>;
    }

    return <>{element}</>;
}