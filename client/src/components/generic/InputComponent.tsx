import React from "react";

type InputProps = {
    type: "text" | "password" | "email";
    name: string;
    labelName: string;
    disabled?: boolean;
    id?: string;
    value?: string;
    onchange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    validators?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    minLength?: number;
    maxLength?: number;
    error?: string;
    onClickButton?: () => void;
    iconButton?: React.ReactNode
}

export default function InputComponent({
                                           type,
                                           name,
                                           id,
                                           labelName,
                                           disabled,
                                           value,
                                           onchange,
                                           minLength,
                                           maxLength,
                                           error,
                                           onClickButton,
                                           iconButton
                                       }: InputProps) {

    const currentDivId: string = 'div_' + (id ?? name);
    const currentLabeld: string = 'label_' + (id ?? name);
    const currentInputd: string = 'input_' + (id ?? name);
    const currentButtonId: string = 'button_' + (id ?? name);


    return <div id={currentDivId}>
        <label id={currentLabeld} htmlFor={id} className="block text-lg text-[#fdeccd]">{labelName}</label>
        <div className="relative">
            <input
                type={type}
                name={name}
                disabled={disabled ?? false}
                id={currentInputd}
                value={value}
                onChange={onchange}
                minLength={minLength}
                maxLength={maxLength}
                className={'w-full p-3 border border-[#fdeccd] rounded-md focus:outline-none focus:ring-2 focus:ring-[#fdeccd] text-#fdeccd]' + (onClickButton ? ' pr-8' : '')}
            />

            {onClickButton && <button
                type="button"
                disabled={disabled ?? false}
                id={currentButtonId}
                className="absolute right-3 top-1/2 transform -translate-y-1/2 text-[#fdeccd] focus:outline-none"
                onClick={onClickButton}
            >
                {iconButton}
            </button>}
        </div>
        {error && <p className="text-red-500 text-sm">{error}</p>}
    </div>
}