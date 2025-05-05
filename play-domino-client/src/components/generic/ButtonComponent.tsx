import {ButtonHTMLAttributes, FC, ReactNode} from "react";

interface ButtonComponentProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    loading?: boolean;
    label: string | ReactNode;
    labelLoading?: string;
    ignoreGenericProps?: boolean;
    bgColor?: string;
}

const ButtonComponent: FC<ButtonComponentProps> = ({
                                                       loading = false,
                                                       label,
                                                       ignoreGenericProps = false,
                                                       labelLoading = "Enviando...",
                                                       className = "",
                                                       bgColor = "bg-[#fdeccd]",
                                                       ...props
                                                   }: ButtonComponentProps) => {
    return (
        <button
            disabled={loading || props.disabled}
            className={`
                ${ignoreGenericProps ? '' : `py-3 px-6 rounded-md font-semibold transition ${bgColor}`} 
                ${className}
                ${loading || props.disabled ? (ignoreGenericProps ? '' : `${bgColor}/60 cursor-not-allowed`) : (ignoreGenericProps ? '' : `${bgColor} hover:brightness-95`)}
            `}
            {...props}
        >
            {loading ? (
                <span className="flex items-center justify-center gap-2">
                    <div className="h-4 w-4 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                    {labelLoading}
                </span>
            ) : (
                label
            )}
        </button>
    );
}

export default ButtonComponent;