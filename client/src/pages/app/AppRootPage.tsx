import {TopBar} from "../../components/app/topbar/TopBar.tsx";
import {Outlet} from "react-router-dom";

export default function AppRootPage() {
    return (
        <div className="min-h-screen flex flex-col bg-zinc-800 text-[#fdeccd]">
            <TopBar/>
            <main className="flex-1 flex items-center justify-center px-4 py-6">
                <Outlet/>
            </main>
        </div>
    )
}