import React, {FC, useEffect, useRef} from "react";
import ReactDOM from "react-dom";

type GenericModalComponentProps = {
    isOpen: boolean;
    onClose: () => void;

    child: React.ReactNode;
}

const GenericModalComponent: FC<GenericModalComponentProps> = ({child, isOpen, onClose}) => {
    const modalRef = useRef<HTMLDivElement>(null);

    // Fechar com ESC
    useEffect(() => {
        const onKeyDown = (e: KeyboardEvent) => {
            if (e.key === "Escape") onClose();
        };
        window.addEventListener("keydown", onKeyDown);
        return () => window.removeEventListener("keydown", onKeyDown);
    }, [onClose]);

    // Fechar clicando fora
    useEffect(() => {
        const onClickOutside = (e: MouseEvent) => {
            if (modalRef.current && !modalRef.current.contains(e.target as Node)) {
                onClose();
            }
        };
        if (isOpen) document.addEventListener("mousedown", onClickOutside);
        return () => document.removeEventListener("mousedown", onClickOutside);
    }, [isOpen, onClose]);

    if (!isOpen) return null;

    return ReactDOM.createPortal(
        <div className="fixed inset-0 bg-black/60 flex items-center justify-center z-50 px-4">
            <div ref={modalRef}
                 className="bg-zinc-900 border border-zinc-700 rounded-xl p-6 w-full max-w-sm relative text-[#fdeccd] shadow-lg"
            >
                {child}
            </div>
        </div>,
        document.body
    );
}

export default GenericModalComponent;