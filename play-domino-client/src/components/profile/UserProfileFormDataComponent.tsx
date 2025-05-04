import GenericFormComponent, {FieldConfig} from "../generic/GenericFormComponent.tsx";
import {FC} from "react";
import {CurrentUser, UserUpdate} from "../../models/users.models.ts";
import {formatCPF, isCpfValid} from "../../utils/cpfUtils.ts";
import {useUpdateMe} from "../../api/user/useUpdateMe.ts";
import {ErrorUtils} from "../../utils/errorUtils.ts";
import {onlyStringDigits} from "../../utils/stringUtils.ts";

type UserProfileFormDataProps = {
    user: CurrentUser;
}

const UserProfileFormDataComponent: FC<UserProfileFormDataProps> = ({
                                                                        user
                                                                    }) => {
    const updateMutation = useUpdateMe();
    const fields: FieldConfig[] = [
        {
            name: "email",
            label: "E-mail",
            type: "email",
            value: user.email,
            disabled: true,
        },
        {
            name: 'username',
            label: 'Usuário',
            type: "text",
            value: user.username,
            disabled: true
        },
        {
            name: 'firstname',
            label: 'Nome',
            type: "text",
            value: user.firstname,
            maxLength: 24
        },
        {
            name: 'lastname',
            label: 'Sobrenome',
            type: "text",
            value: user.lastname,
            maxLength: 24
        },
        {
            name: "federalDocument",
            label: "CPF",
            type: "text",
            value: user.federalDocument,
            disabled: user.federalDocument !== null && user.federalDocument !== undefined,
            maxLength: 14,
            valueMapper: onlyStringDigits,
            format: formatCPF,
            helpText: 'O CPF é necessário para validar sua identidade e permitir transações financeiras, como apostas e saques.',
            validate: (value) => {
                if (value === '' || value === null || value === undefined) {
                    return null;
                }
                if (!/^\d{3}\.\d{3}\.\d{3}-\d{2}$/.test(value)) return "CPF com formato inválido!";
                if (!isCpfValid(value)) return "CPF inválido. Dígito verificador incorreto!";
                return null;
            },
        }
    ];
    const handleSubmit = (data: Record<string, string>) => {
        const keysToSubmit: (keyof UserUpdate)[] = ['firstname', 'lastname', 'federalDocument'];

        const dataToSubmit: UserUpdate = {
            firstname: user.firstname,
            lastname: user.lastname,
            federalDocument: user.federalDocument,
        };
        keysToSubmit.forEach((it) => {
            if (user[it] !== data[it]) {
                dataToSubmit[it] = data[it];
            }
        });

        if (Object.values(dataToSubmit).filter(it => it !== null).length === 0) {
            return;
        }

        updateMutation.mutate(dataToSubmit, {
            onSuccess: () => {
                window.location.reload();
            },
            onError: (err) => {
                ErrorUtils.displayAxiosError(err)
            }
        })
    };

    return (
        <GenericFormComponent fields={fields}
                              onSubmit={handleSubmit}
                              submitLabel="Salvar"
                              labelLoading="Salvando"
                              formDivClassName={"grid grid-cols-1 sm:grid-cols-2 gap-6"}
                              isLoading={updateMutation.isPending}
        />
    );
}

export default UserProfileFormDataComponent;