import GenericFormComponent, {FieldConfig} from "../generic/GenericFormComponent";
import {formatCPF, isCpfValid} from "../../utils/cpfUtils.ts";
import {toast} from "sonner";
import {AxiosError} from "axios";
import {ErrorMessage} from "../../models/error-message";
import {RegisterPayload} from "../../models/auth.models.ts";
import {useRegisterUser} from "../../api/auth/useRegisterUser.ts";

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
        {name: "firstname", label: "Nome", type: "text", required: true},
        {name: "lastname", label: "Sobrenome", type: "text", required: true},
        {
            name: "federalDocument",
            label: "CPF",
            type: "text",
            format: formatCPF,
            required: true,
            validate: (value) => {
                if (!/^\d{3}\.\d{3}\.\d{3}-\d{2}$/.test(value)) return "Formato inválido";
                if (!isCpfValid(value)) return "CPF inválido";
                return null;
            },
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

    const handleSubmit = (data: Record<string, string>) => {
        mutation.mutate(
            {...(data as RegisterPayload), country: "BRAZIL"},
            {
                onSuccess: () => {

                },
                onError: (error: Error) => {
                    const axiosError = error as AxiosError;
                    toast.error((axiosError.response?.data as ErrorMessage).message.toString());
                },
            }
        );
    };

    return (
        <GenericFormComponent
            fields={fields}
            onSubmit={handleSubmit}
            submitLabel="Cadastrar"
            isLoading={mutation.isPending}
        />
    );
}
