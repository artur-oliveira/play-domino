import {TopBar} from "../../components/app/topbar/TopBar.tsx";
import {Outlet} from "react-router-dom";

const AppRootPage = () => {
    return (
        <div className="min-h-screen flex flex-col bg-zinc-800 text-[#fdeccd]">
            <TopBar/>
            <Outlet/>
        </div>
    )
}
export default AppRootPage;