import React, {useEffect, useRef, useState} from "react";
import ButtonComponent from "../generic/ButtonComponent.tsx";
import {toast} from "sonner";
import {useNavigate} from "react-router-dom";
import {AxiosError} from "axios";
import {ErrorMessage} from "../../models/error-message.ts";
import InputComponent from "../generic/InputComponent.tsx";
import {useConfirmCode} from "../../api/auth.api.ts";

type Props = {
    length?: number;
};

export default function ConfirmCodeComponent({length = 6}: Props) {
    const mutation = useConfirmCode();
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();
    const hasEmailToken = localStorage.getItem('new_user_email');
    const [isLoadingResend, setIsLoadingResend] = useState(false);
    const [values, setValues] = useState(Array(length).fill(""));
    const inputsRef = useRef<(HTMLInputElement | null)[]>([]);

    const handleChange = (index: number, value: string) => {
        const newValues = [...values];
        newValues[index] = value.slice(-1).toUpperCase();
        setValues(newValues);

        if (value && index < length - 1) {
            inputsRef.current[index + 1]?.focus();
        }
    };

    const handleKeyDown = (e: React.KeyboardEvent, index: number) => {
        if (e.key === 'Backspace' && !values[index] && index > 0) {
            inputsRef.current[index - 1]?.focus();
        }
    };

    const handleSubmit = (e: React.MouseEvent) => {
        e.preventDefault();
        if (isLoading) {
            return;
        }
        setIsLoading(true);
        try {
            const code = values.join("");
            mutation.mutate(code, {
                onSuccess: () => {
                    toast.success('Verificação concluída!');
                    navigate("/auth/login");
                },
                onError: (error: Error) => {
                    const axiosError = error as AxiosError;
                    toast.error((axiosError.response?.data as ErrorMessage).message.toString());
                }
            })
        } finally {
            setIsLoading(false);
        }
    };

    const handleResendSubmit = (e: React.MouseEvent) => {
        e.preventDefault();
        if (isLoadingResend) {
            return;
        }
        setIsLoadingResend(true);
        try {
            const code = values.join("");
            // TODO: Implement resend submit
            console.info(`Not implemented code ${code}`)
        } finally {
            setIsLoadingResend(false);
        }
    };

    const isButtonDisabled = values.some(val => val === "");

    useEffect(() => {
        inputsRef.current[0]?.focus();
    }, []);

    return (
        <div className="space-y-4">
            <div className="flex justify-center gap-2">
                {values.map((val, i) => (
                    <InputComponent
                        key={i}
                        ref={(el) => {
                            inputsRef.current[i] = el;
                        }}
                        value={val}
                        onChange={(e) => handleChange(i, e.target.value)}
                        onKeyDown={(e) => handleKeyDown(e, i)}
                        className="w-12 h-12 text-center text-lg rounded-md border border-zinc-400 bg-zinc-800 text-[#fdeccd] focus:outline-none focus:ring-2 focus:ring-yellow-400"
                        maxLength={1}
                        autoComplete="off"
                        inputMode="numeric"
                    />
                ))}
            </div>
            <div className="text-center">
                <ButtonComponent
                    className="bg-[#fdeccd] text-zinc-900 py-2 px-4 rounded-lg font-semibold hover:opacity-90 transition disabled:opacity-40"
                    loading={isLoading}
                    label={"Confirmar Código"}
                    onClick={handleSubmit}
                    labelLoading={"Confirmando..."}
                    disabled={isButtonDisabled}
                    type="button"/>
                {hasEmailToken && <div onClick={handleResendSubmit}
                                       className="text-sm text-[#fdeccd] mt-2 cursor-pointer hover:underline">
                    Reenviar código
                </div>}
            </div>
        </div>
    );
}
