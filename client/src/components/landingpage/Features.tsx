import {HiCubeTransparent, HiShieldCheck, HiUserGroup} from "react-icons/hi2";

export default function Features() {
    return (
        <section className="mt-8 text-[#fdeccd] px-4 w-full max-w-7xl mx-auto">
            <h2 className="text-3xl font-bold text-center mb-10">Por que jogar com a gente?</h2>
            <div className="grid md:grid-cols-3 gap-8">
                <div
                    className="bg-zinc-900/60 backdrop-blur rounded-xl p-6 text-center shadow-md hover:scale-105 transition">
                    <HiCubeTransparent className="text-5xl mx-auto mb-4 text-[#fdeccd]"/>
                    <h3 className="text-xl font-semibold">100% Justo</h3>
                    <p className="text-sm mt-2">Sistema auditado e embaralhamento imparcial. Sem truques.</p>
                </div>
                <div
                    className="bg-zinc-900/60 backdrop-blur rounded-xl p-6 text-center shadow-md hover:scale-105 transition">
                    <HiShieldCheck className="text-5xl mx-auto mb-4 text-[#fdeccd]"/>
                    <h3 className="text-xl font-semibold">Apostas Seguras</h3>
                    <p className="text-sm mt-2">Controle total do seu saldo, com saques rápidos e transparentes.</p>
                </div>
                <div
                    className="bg-zinc-900/60 backdrop-blur rounded-xl p-6 text-center shadow-md hover:scale-105 transition">
                    <HiUserGroup className="text-5xl mx-auto mb-4 text-[#fdeccd]"/>
                    <h3 className="text-xl font-semibold">Online com Amigos</h3>
                    <p className="text-sm mt-2">Convide e jogue partidas privadas com seus amigos.</p>
                </div>
            </div>
        </section>)
}