import ConfirmCodeComponent from "../../components/auth/ConfirmCodeComponent.tsx";


const ConfirmCodePage = () => {
    return (
        <div className="min-h-screen flex items-center justify-center bg-zinc-900 px-4">
            <div className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow p-8 max-w-md w-full text-center">
                <h1 className="text-2xl font-bold text-[#fdeccd] mb-4">Confirme seu E-mail</h1>
                <p className="text-[#fdeccd] text-sm mb-6">
                    Insira o código que enviamos para seu e-mail.
                </p>
                <ConfirmCodeComponent/>
            </div>
        </div>
    );
}

export default ConfirmCodePage;