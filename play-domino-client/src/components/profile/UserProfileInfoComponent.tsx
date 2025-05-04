import {CurrentUser} from "../../models/users.models.ts";
import {Skeleton} from "../generic/Skeleton.tsx";
import {FaUserCircle} from "react-icons/fa";
import UserProfileAchievementsComponent from "./UserProfileAchievementsComponent.tsx";
import UserProfileFormDataComponent from "./UserProfileFormDataComponent.tsx";

type Props = {
    user: CurrentUser | undefined;
    isLoading: boolean;
};

const ProfileVisualComponent = ({user}: { user: CurrentUser }) => (
    <div className="flex items-center gap-4 mb-6">
        <div className="text-5xl text-yellow-500 bg-zinc-700 p-4 rounded-full shadow">
            <FaUserCircle/>
        </div>
        <div>
            <h2 className="text-xl font-semibold">{user.name}</h2>
            <p className="text-zinc-400 text-sm">Jogador desde {user.createdAt.split('-')[0]} • Ranking -</p>
        </div>
    </div>
);


const UserProfileInfoComponent = ({user, isLoading}: Props) => {
    if (isLoading || !user) {
        return <Skeleton className="rounded-md h-48"/>;
    }

    return (
        <>
            <ProfileVisualComponent user={user}/>
            <UserProfileFormDataComponent user={user}/>
            <UserProfileAchievementsComponent/>
        </>
    );
};

export default UserProfileInfoComponent;
