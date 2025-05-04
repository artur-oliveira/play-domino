import {ButtonHTMLAttributes, FC, ReactNode} from "react";

interface ButtonComponentProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    loading?: boolean;
    label: string | ReactNode;
    labelLoading?: string;
    bgColor?: string;
}

const ButtonComponent: FC<ButtonComponentProps> = ({
                                                       loading = false,
                                                       label,
                                                       labelLoading = "Enviando...",
                                                       className = "",
                                                       bgColor = "bg-[#fdeccd]",
                                                       ...props
                                                   }: ButtonComponentProps) => {
    return (
        <button
            disabled={loading || props.disabled}
            className={`
                py-3 px-6 rounded-md font-semibold transition ${bgColor}
                ${className}
                ${loading || props.disabled ? `${bgColor}/60 cursor-not-allowed` : `${bgColor} hover:brightness-95`}
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