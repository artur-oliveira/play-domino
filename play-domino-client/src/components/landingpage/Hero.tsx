import {Link} from "react-router-dom";

export default function Hero() {
    return (<div
        className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-8 max-w-7xl w-full self-center flex justify-between items-center">
        <div className="text-center md:text-left space-y-6">
            <h1 className="text-4xl font-bold text-[#fdeccd] drop-shadow-lg">
                Jogue dominó online. Aposte, vença, repita.
            </h1>
            <p className="text-lg text-[#fdeccd] drop-shadow">
                Partidas justas, divertidas e com emoção de verdade. Bora jogar?
            </p>
            <div className="flex justify-center md:justify-start gap-4 pt-4">
                <Link
                    to="/auth/register"
                    className="bg-[#fdeccd] text-black font-semibold py-1.5 px-4 rounded-full hover:opacity-80 transition">
                    Quero Jogar
                </Link>
            </div>
        </div>
        <div className="flex">
            <img
                src="/assets/images/play-domino.png"
                alt="Dominó Online"
                className="rounded-xl max-w-xs md:max-w-md transition-transform duration-300 hover:scale-105 animate-float"
            />
        </div>
    </div>)

}