import GenericFormComponent, {FieldConfig} from "../generic/GenericFormComponent";
import {toast} from "sonner";
import {ErrorMessage} from "../../models/error-message";
import {LoginPayload} from "../../models/auth.models.ts";
import {useLoginUser} from "../../api/auth/useLoginUser.ts";
import {useAuth} from "../../providers/auth/useAuth.tsx";
import {useNavigate} from "react-router-dom";
import {isAxiosError} from "axios";

export default function LoginComponent() {
    const mutation = useLoginUser();
    const navigate = useNavigate();
    const auth = useAuth();
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
            name: "password",
            label: "Senha",
            type: "password",
            required: true,
            iconToggle: true,
            validate: (value) =>
                /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,128}$/.test(value)
                    ? null
                    : "Senha fraca. A senha deve possuir um tamanho maior que 8 caracteres, incluindo letras maiúsculas, minúsculas, números e símbolos",
        }
    ];

    const handleSubmit = (data: Record<string, string>) => {
        mutation.mutate(
            {...(data as LoginPayload)},
            {
                onSuccess: (res) => {
                    auth.login(res.data.accessToken);
                    navigate("/")
                },
                onError: (error: Error) => {
                    if (isAxiosError(error)) {
                        if (error.response && error.response.data) {
                            toast.error((error.response?.data as ErrorMessage).message.toString());
                        } else {
                            toast.error(error.message.toString());
                        }
                    }
                    console.error(error);
                },
            }
        );
    };

    return (
        <GenericFormComponent
            fields={fields}
            onSubmit={handleSubmit}
            submitLabel="Entrar"
            isLoading={mutation.isPending}
        />
    );
}
