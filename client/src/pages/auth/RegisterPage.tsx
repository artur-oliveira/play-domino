import {Link} from 'react-router-dom';
import RegisterComponent from "../../components/auth/RegisterComponent.tsx";


export default function RegisterPage() {

    return (
        <div className="min-h-screen flex items-center justify-center bg-zinc-800/50 backdrop-blur-md px-4 py-6">
            <div className="max-w-lg w-full bg-zinc-800/50 backdrop-blur-md shadow-lg rounded-xl p-6">
                <h2 className="text-2xl font-semibold text-center mb-6 text-[#fdeccd]">Nova Conta</h2>
                <RegisterComponent/>
                <div className="mt-4 text-center">
                    <p>
                        Já tem uma conta?{' '}
                        <Link to="/auth/login" className="text-[#fdeccd] hover:underline">Clique aqui</Link>
                    </p>
                </div>
            </div>
        </div>
    );
}
