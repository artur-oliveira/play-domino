import {Link} from "react-router-dom";
import Logo from "../generic/Logo.tsx";

const TopBar = () => {
    return (
        <header
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center flex justify-between items-center">

            <div className="flex items-center gap-4">
                <Link to="/">
                    <Logo className="h-8 w-8 text-[#fdeccd]"/>
                </Link>
            </div>

            <div className="flex items-center gap-4">
                <Link
                    to="/auth/login"
                    className="text-[#fdeccd] font-medium py-1.5 px-4 rounded-full hover:opacity-80 transition"
                >
                    Entrar
                </Link>

                <Link
                    to="/auth/register"
                    className="bg-[#fdeccd] text-black font-semibold py-1.5 px-4 rounded-full hover:opacity-80 transition"
                >
                    Quero Jogar
                </Link>
            </div>
        </header>
    )
}

export default TopBar;