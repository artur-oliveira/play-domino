import RegisterComponent from "../../components/auth/RegisterComponent.tsx";
import TopBar from "../../components/landingpage/TopBar.tsx";


export default function RegisterPage() {

    return (
        <div className="min-h-screen flex flex-col bg-zinc-800 text-[#fdeccd]">
            <TopBar/>
            <main className="flex-1 flex items-center justify-center px-4 py-6">
                <RegisterComponent/>
            </main>
        </div>
    );
}
