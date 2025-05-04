// src/components/ConfirmationModal.tsx
import React from "react";
import GenericModalComponent from "./GenericModalComponent.tsx";
import ButtonComponent from "./ButtonComponent.tsx";

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
                    <h3 className="text-xl font-semibold text-center text-[#fdeccd]">{label}</h3>
                    <div className="flex justify-around mt-4">
                        <ButtonComponent
                            label="Voltar"
                            onClick={onClose}
                            className="bg-gray-600 text-[#fdeccd] px-4 py-2 rounded-md hover:bg-gray-500 transition"
                        >
                        </ButtonComponent>
                        <ButtonComponent
                            label="Confirmar"
                            onClick={onConfirm}
                            className="bg-red-600 text-[#fdeccd] px-4 py-2 rounded-md hover:bg-red-500 transition"
                        >
                        </ButtonComponent>
                    </div>
                </>
            }
        />
    );
};

export default ConfirmationModal;
