// src/components/ConfirmationModal.tsx
import React from "react";
import GenericModalComponent from "./GenericModalComponent.tsx";

interface ConfirmationModalProps {
    label: string;
    isOpen: boolean;
    onClose: () => void;
    onConfirm: () => void;
}

const ConfirmationModal: React.FC<ConfirmationModalProps> = ({label, isOpen, onClose, onConfirm}) => {
    return (
        <GenericModalComponent
            isOpen={isOpen}
            onClose={onClose}
            child={
                <>
                    <h3 className="text-xl font-semibold text-center text-white">{label}</h3>
                    <div className="flex justify-around mt-4">
                        <button
                            onClick={onClose}
                            className="bg-gray-600 text-white px-4 py-2 rounded-md hover:bg-gray-500 transition"
                        >
                            Voltar
                        </button>
                        <button
                            onClick={onConfirm}
                            className="bg-red-600 text-white px-4 py-2 rounded-md hover:bg-red-500 transition"
                        >
                            Confirmar
                        </button>
                    </div>
                </>
            }
        />
    );
};

export default ConfirmationModal;
