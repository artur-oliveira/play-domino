import GenericFormComponent, {FieldConfig} from "../generic/GenericFormComponent";
import {toast} from "sonner";
import {AxiosError} from "axios";
import {ErrorMessage} from "../../models/error-message";
import {RegisterPayload} from "../../models/auth.models.ts";
import {useRegisterUser} from "../../api/auth/useRegisterUser.ts";
import {Link, useNavigate} from "react-router-dom";

export default function RegisterComponent() {
    const mutation = useRegisterUser();
    const fields: FieldConfig[] = [
        {
            name: "email",
            label: "Email",
            type: "email",
            required: true,
            validate: (value) =>
                /^[\w.%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value) ? null : "Email inválido",
        },
        {
            name: "username",
            label: "Nome de usuário",
            type: "text",
            required: true,
            validate: (value) =>
                /^(?!.*[._]{2})(?!.*[._]$)[a-zA-Z][a-zA-Z0-9._]{2,31}$/.test(value)
                    ? null
                    : "Usuário inválido",
        },
        {
            name: "password",
            label: "Senha",
            type: "password",
            required: true,
            iconToggle: true,
            validate: (value) =>
                /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,128}$/.test(value)
                    ? null
                    : "Senha fraca. A senha deve possuir um tamanho maior que 8 caracteres, incluindo letras maiúsculas, minúsculas, números e símbolos",
        },
        {
            name: "repeat_password",
            label: "Confirmação de senha",
            type: "password",
            required: true,
            iconToggle: true,
            validate: (value, formData) =>
                value === formData.password ? null : "Senhas não coincidem",
        },

    ];

    const navigate = useNavigate();

    const handleSubmit = (data: Record<string, string>) => {
        mutation.mutate(
            {...(data as RegisterPayload), country: "BRAZIL"},
            {
                onSuccess: () => {
                    navigate("/auth/confirm")
                },
                onError: (error: Error) => {
                    const axiosError = error as AxiosError;
                    toast.error((axiosError.response?.data as ErrorMessage).message.toString());
                },
            }
        );
    };

    return (
        <div className="w-full max-w-lg bg-zinc-800/50 backdrop-blur-md shadow-lg rounded-xl p-6">
            <h2 className="text-2xl font-semibold text-center mb-6 text-[#fdeccd]">Nova Conta</h2>
            <GenericFormComponent
                fields={fields}
                onSubmit={handleSubmit}
                submitLabel="Cadastrar"
                isLoading={mutation.isPending}
            />
            <div className="mt-4 text-center">
                <p>
                    Já tem uma conta?{' '}
                    <Link to="/auth/login" className="text-[#fdeccd] hover:underline">Clique aqui</Link>
                </p>
            </div>
        </div>

    );
}
