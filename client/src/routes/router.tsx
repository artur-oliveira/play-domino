import {createBrowserRouter} from "react-router-dom";
import LandingPage from "../pages/LandingPage.tsx";
import RegisterPage from "../pages/auth/RegisterPage.tsx";
import ConfirmPage from "../pages/auth/ConfirmCodePage.tsx";
import NotFoundPage from "../pages/NotFoundPage.tsx";
import MaintenancePage from "../pages/Maintenance.tsx";
import {ProtectedRoute} from "./protected.route.tsx";
import {NotLoggedRoute} from "./not-logged.route.tsx";
import LoginPage from "../pages/auth/LoginPage.tsx";
import DominoAppPage from "../pages/app/DominoAppPage.tsx";
import AppRootPage from "../pages/app/AppRootPage.tsx";
import UserProfilePage from "../pages/app/UserProfilePage.tsx";
import UserWalletPage from "../pages/app/UserWalletPage.tsx";


export const router = createBrowserRouter([
    {
        path: "/",
        element: <LandingPage/>,
    },
    {
        path: "/auth/login",
        element: <NotLoggedRoute element={<LoginPage/>}/>,
    },
    {
        path: "/auth/register",
        element: <NotLoggedRoute element={<RegisterPage/>}/>,
    },
    {
        path: "/auth/confirm",
        element: <NotLoggedRoute element={<ConfirmPage/>}/>,
    },
    {
        path: "/app",
        element: <ProtectedRoute
            element={<AppRootPage/>}
        />,
        children: [
            {
                index: true,
                element: <DominoAppPage/>
            },
            {
                path: "/app/profile",
                element: <UserProfilePage/>
            },
            {
                path: "/app/wallet",
                element: <UserWalletPage/>
            },
        ]
    },
    {
        path: "maintenance",
        element: <MaintenancePage/>,
    },
    {
        path: "*",
        element: <NotFoundPage/>,
    },
]);