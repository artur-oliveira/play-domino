import React, {FC, useState} from "react";
import InputComponent from "../generic/InputComponent";
import {AiOutlineEye, AiOutlineEyeInvisible} from "react-icons/ai";
import ButtonComponent from "./ButtonComponent.tsx";

export type ValueMapper = (value: string) => string;

export interface FieldConfig {
    name: string;
    label: string;
    type: 'text' | 'email' | 'password';
    value?: string | number | undefined | null;
    disabled?: boolean;
    required?: boolean;
    minLength?: number;
    maxLength?: number;
    helpText?: string;
    format?: ValueMapper;
    valueMapper?: ValueMapper;
    validate?: (value: string, formData: Record<string, string>) => string | null;
    iconToggle?: boolean;
}

interface GenericFormProps {
    formClassName?: string;
    formDivClassName?: string;
    fields: FieldConfig[];
    onSubmit: (formData: Record<string, string>) => void;
    submitLabel: string;
    isLoading: boolean;
    labelLoading?: string;
}

const GenericFormComponent: FC<GenericFormProps> = ({
                                                        formClassName,
                                                        formDivClassName,
                                                        fields,
                                                        onSubmit,
                                                        submitLabel,
                                                        isLoading,
                                                        labelLoading,
                                                    }) => {
    const defaultMapper: ValueMapper = (val) => val;

    const [formData, setFormData] = useState<Record<string, string>>(() =>
        Object.fromEntries(fields.map((field) => [field.name, (field.format || defaultMapper)((field.value || '').toString())]))
    );
    const [errors, setErrors] = useState<Record<string, string>>({});
    const [passwordVisible, setPasswordVisible] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        const field = fields.find((f) => f.name === name);
        setFormData((prev) => ({
            ...prev,
            [name]: field?.format ? field.format(value) : value,
        }));
    };

    const validateForm = () => {
        let isValid = true;
        const newErrors: Record<string, string> = {};

        fields.forEach((field) => {
            const value = formData[field.name];
            if (field.required && !value.trim()) {
                newErrors[field.name] = "Campo obrigatório";
                isValid = false;
            } else if (field.validate) {
                const error = field.validate(value, formData);
                if (error) {
                    newErrors[field.name] = error;
                    isValid = false;
                }
            }
        });

        setErrors(newErrors);
        return isValid;
    };

    const applyFormDataMapper = () => {
        const newFormData: Record<string, string> = {};
        fields.forEach(it => {
            newFormData[it.name] = (it.valueMapper || defaultMapper)(formData[it.name]) || '';
        });
        return newFormData;
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!isLoading && validateForm()) {
            onSubmit(applyFormDataMapper());
        }
    };
    const allDisabled = fields.filter(it => it.disabled).length === fields.length;

    return (
        <form onSubmit={handleSubmit} className={formClassName ? formClassName : "space-y-4"}>
            <div className={formDivClassName ?? 'space-y-4'}>
                {fields.map((field) => (
                    <InputComponent
                        key={field.name}
                        type={
                            field.type === "password" && field.iconToggle
                                ? passwordVisible
                                    ? "text"
                                    : "password"
                                : field.type
                        }
                        disabled={field.disabled}
                        name={field.name}
                        labelName={field.label}
                        onchange={handleChange}
                        value={(field.format || defaultMapper)(formData[field.name])}
                        helpText={field.helpText}
                        minLength={field.minLength}
                        maxLength={field.maxLength}
                        error={errors[field.name]}
                        onClickButton={
                            field.iconToggle ? () => setPasswordVisible(!passwordVisible) : undefined
                        }
                        iconButton={
                            field.iconToggle
                                ? passwordVisible
                                    ? <AiOutlineEyeInvisible/>
                                    : <AiOutlineEye/>
                                : undefined
                        }
                    />
                ))}
            </div>

            <div className="border-t border-zinc-700 mt-6"/>

            {/* Botão destacado */}
            <div className="flex justify-center">
                <ButtonComponent
                    className="bg-[#fdeccd] text-zinc-900 py-2 px-4 rounded-lg font-semibold hover:opacity-90 transition disabled:opacity-40"
                    type="submit"
                    loading={isLoading}
                    label={submitLabel}
                    disabled={allDisabled}
                    labelLoading={labelLoading}
                />
            </div>
        </form>
    );
}

export default GenericFormComponent