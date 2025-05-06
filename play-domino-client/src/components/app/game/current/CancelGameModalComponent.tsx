import {FC} from "react";
import GenericModalComponent from "../../../generic/GenericModalComponent.tsx";
import ButtonComponent from "../../../generic/ButtonComponent.tsx";

type CancelGameModalProps = {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: (approved: boolean) => void;
}

const CancelGameModalComponent: FC<CancelGameModalProps> = ({
                                                                isOpen,
                                                                onClose,
                                                                onConfirm
                                                            }) => {
    return (
        <GenericModalComponent
            isOpen={isOpen}
            onClose={onClose}
            child={
                <>
                    <h3 className="text-xl font-semibold text-center text-[#fdeccd]">
                        Aceita o cancelamento deste jogo?
                    </h3>
                    <div className="flex justify-around mt-4">
                        <ButtonComponent
                            label="Não"
                            onClick={() => onConfirm(false)}
                            className="bg-green-600 text-[#fdeccd] px-4 py-2 rounded-md hover:bg-green-500 transition"
                        >
                        </ButtonComponent>
                        <ButtonComponent
                            label="Sim"
                            onClick={() => onConfirm(true)}
                            className="bg-red-600 text-[#fdeccd] px-4 py-2 rounded-md hover:bg-red-500 transition"
                        >
                        </ButtonComponent>
                    </div>
                </>
            }
        />
    )
}

export default CancelGameModalComponent;