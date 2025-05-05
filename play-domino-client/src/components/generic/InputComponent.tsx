import React, {FC, InputHTMLAttributes, RefAttributes} from "react";

interface InputComponentProps extends InputHTMLAttributes<HTMLInputElement>, RefAttributes<HTMLInputElement> {
    labelName?: string;
    labelClassName?: string;
    onchange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    validators?: (e: React.ChangeEvent<HTMLInputElement>) => void;
    error?: string | null;
    onClickButton?: () => void;
    iconButton?: React.ReactNode
    helpText?: string;
}

const InputComponent: FC<InputComponentProps> = ({
                                                     type,
                                                     labelClassName,
                                                     name,
                                                     helpText,
                                                     id,
                                                     labelName,
                                                     disabled,
                                                     value,
                                                     onchange,
                                                     minLength,
                                                     maxLength,
                                                     error,
                                                     onClickButton,
                                                     iconButton,
                                                     ...props
                                                 }) => {

    const currentDivId: string = 'div_' + (id ?? name);
    const currentLabeld: string = 'label_' + (id ?? name);
    const currentInputd: string = 'input_' + (id ?? name);
    const currentButtonId: string = 'button_' + (id ?? name);


    return <div id={currentDivId}>
        {labelName && <label id={currentLabeld} htmlFor={id}
                             className={labelClassName ? labelClassName : "block text-lg text-[#fdeccd]"}>{labelName}</label>}
        <div className={onClickButton ? 'relative' : ''}>
            <input
                type={type}
                name={name}
                disabled={disabled ?? false}
                id={currentInputd}
                value={value}
                onChange={onchange}
                minLength={minLength}
                maxLength={maxLength}
                className={
                    `w-full p-3 border border-[#fdeccd] rounded-md text-[#fdeccd] bg-transparent 
                   ${disabled ? 'opacity-50 cursor-not-allowed' : 'focus:outline-none focus:ring-2 focus:ring-[#fdeccd]'} 
                   ${onClickButton ? ' pr-8' : ''}`
                }
                {...props}
            />

            {onClickButton && <button
                type="button"
                disabled={disabled ?? false}
                id={currentButtonId}
                className={`absolute right-3 top-1/2 transform -translate-y-1/2 
                        ${disabled ? 'opacity-50 cursor-not-allowed' : 'text-[#fdeccd] focus:outline-none'}`}
                onClick={onClickButton}
            >
                {iconButton}
            </button>}
        </div>
        {error && <p className="text-red-500 text-sm mt-1">{error}</p>}
        {!error && helpText && (
            <p className="text-sm text-zinc-400 mt-1">{helpText}</p>
        )}
    </div>
}

export default InputComponent;