import React, { useState } from "react";
import InputComponent from "../generic/InputComponent";
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import ButtonComponent from "./ButtonComponent.tsx";

export interface FieldConfig {
    name: string;
    label: string;
    type: "text" | "email" | "password";
    required?: boolean;
    minLength?: number;
    maxLength?: number;
    format?: (value: string) => string;
    validate?: (value: string, formData: Record<string, string>) => string | null;
    iconToggle?: boolean;
}

interface GenericFormProps {
    fields: FieldConfig[];
    onSubmit: (formData: Record<string, string>) => void;
    submitLabel: string;
    isLoading: boolean;
}

export default function GenericFormComponent({
                                                 fields,
                                                 onSubmit,
                                                 submitLabel,
                                                 isLoading,
                                             }: GenericFormProps) {
    const [formData, setFormData] = useState<Record<string, string>>(() =>
        Object.fromEntries(fields.map(({ name }) => [name, ""]))
    );

    const [errors, setErrors] = useState<Record<string, string>>({});
    const [passwordVisible, setPasswordVisible] = useState(false);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
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

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!isLoading && validateForm()) {
            onSubmit(formData);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="space-y-4">
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
                    name={field.name}
                    labelName={field.label}
                    onchange={handleChange}
                    value={formData[field.name]}
                    minLength={field.minLength}
                    maxLength={field.maxLength}
                    error={errors[field.name]}
                    onClickButton={
                        field.iconToggle ? () => setPasswordVisible(!passwordVisible) : undefined
                    }
                    iconButton={
                        field.iconToggle
                            ? passwordVisible
                                ? <AiOutlineEyeInvisible />
                                : <AiOutlineEye />
                            : undefined
                    }
                />
            ))}

            <div className="pt-4 flex justify-center">
                <ButtonComponent type="submit" loading={isLoading} label={submitLabel} />
            </div>
        </form>
    );
}
