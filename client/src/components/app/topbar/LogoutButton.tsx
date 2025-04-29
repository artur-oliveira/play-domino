import {useNavigate} from "react-router-dom";
import {useAuth} from "../../../providers/auth/useAuth.tsx";
import ConfirmationModal from "../../generic/ConfirmationModal.tsx";
import {useState} from "react";

const LogoutButton = () => {
    const {logout} = useAuth();
    const [isModalOpen, setIsModalOpen] = useState(false);  // Estado para o modal
    const navigate = useNavigate();

    // Função para abrir o modal
    const openModal = () => setIsModalOpen(true);

    // Função para fechar o modal
    const closeModal = () => setIsModalOpen(false);

    const handleLogout = () => {
        logout();
        navigate("/auth/login");
    };

    return (
        <>
            <button
                onClick={openModal}
                className="text-red-400 hover:text-red-300 font-medium transition"
            >
                Sair
            </button>

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
