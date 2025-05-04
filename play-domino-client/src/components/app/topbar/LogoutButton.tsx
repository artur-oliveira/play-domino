import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../providers/auth/useAuth.tsx";
import ConfirmationModal from "../../generic/ConfirmationModal.tsx";
import {useState} from "react";
import ButtonComponent from "../../generic/ButtonComponent.tsx";

const LogoutButton = () => {
    const {logout} = useAuth();
    const [isModalOpen, setIsModalOpen] = useState(false);  // Estado para o modal
    const navigate = useNavigate();
    const openModal = () => setIsModalOpen(true);
    const closeModal = () => setIsModalOpen(false);

    const handleLogout = () => {
        logout();
        navigate("/auth/login");
    };

    return (
        <>
            <ButtonComponent
                label="Sair"
                onClick={openModal}
                className="text-red-400 bg-inherit hover:text-red-300 font-medium transition"
            >
            </ButtonComponent>

            <ConfirmationModal
                label="Tem certeza que deseja sair?"
                isOpen={isModalOpen}
                onClose={closeModal}
                onConfirm={handleLogout}
            />
        </>


    )
}
export default LogoutButton;
