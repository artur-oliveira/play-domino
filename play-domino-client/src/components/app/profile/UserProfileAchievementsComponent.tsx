import {FaMedal} from "react-icons/fa";

const UserProfileAchievementsComponent = () => (
    <div className="mt-10">
        <h3 className="text-lg font-semibold flex items-center gap-2 mb-2">
            <FaMedal className="text-yellow-400" /> Conquistas
        </h3>
        <p className="text-sm text-zinc-400">
            Você ainda não desbloqueou nenhuma conquista. Continue jogando para desbloquear!
        </p>
    </div>
);

export default UserProfileAchievementsComponent;