import {RouterProvider} from 'react-router-dom';
import {router} from "./routes/router.tsx";
import {Toaster} from "sonner";
import {AuthProvider} from "./providers/auth/AuthProvider.tsx";

const App = () => {
    return (
        <AuthProvider >
            <RouterProvider router={router}/>
            <Toaster richColors position="bottom-center"/>
        </AuthProvider>

    );
};

export default App;
