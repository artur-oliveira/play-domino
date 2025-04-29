import {Link} from 'react-router-dom';
import LoginComponent from "../../components/auth/LoginComponent.tsx";


export default function LoginPage() {

    return (
        <div className="min-h-screen flex items-center justify-center bg-zinc-800/50 backdrop-blur-md px-4 py-6">
            <div className="max-w-lg w-full bg-zinc-800/50 backdrop-blur-md shadow-lg rounded-xl p-6">
                <h2 className="text-2xl font-semibold text-center mb-6 text-[#fdeccd]">Entrar</h2>
                <LoginComponent/>
                <div className="mt-4 text-center">
                    <p>
                        Não possui uma conta?{' '}
                        <Link to="/auth/register" className="text-[#fdeccd] hover:underline">Clique aqui</Link>
                    </p>
                </div>
            </div>
        </div>
    );
}
