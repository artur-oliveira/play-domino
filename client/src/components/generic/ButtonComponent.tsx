import {ButtonHTMLAttributes} from "react";

interface ButtonComponentProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    loading?: boolean;
    label: string;
    labelLoading?: string;
}

export default function ButtonComponent({
                                            loading = false,
                                            label,
                                            labelLoading = "Enviando...",
                                            className = "",
                                            ...props
                                        }: ButtonComponentProps) {
    return (
        <button
            disabled={loading || props.disabled}
            className={`
        w-full py-3 px-4 rounded-md font-semibold transition 
        ${loading || props.disabled ? "bg-[#fdeccd]/60 cursor-not-allowed" : "bg-[#fdeccd] hover:brightness-95"}
        text-black 
        ${className}
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
