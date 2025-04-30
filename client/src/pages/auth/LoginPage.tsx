import LoginComponent from "../../components/auth/LoginComponent.tsx";
import TopBar from "../../components/landingpage/TopBar.tsx";


export default function LoginPage() {

    return (
        <div className="min-h-screen flex flex-col bg-zinc-800 text-[#fdeccd]">
            <TopBar />
            <main className="flex-1 flex items-center justify-center px-4 py-6">
                <LoginComponent />
            </main>
        </div>
    );
}
