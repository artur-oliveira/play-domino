import UserProfileHeaderComponent from "../../components/app/profile/UserProfileHeaderComponent.tsx";
import UserProfileInfoComponent from "../../components/app/profile/UserProfileInfoComponent.tsx";
import {useUser} from "../../providers/user/useUser.tsx";

const UserProfilePage = () => {
    const {user, isLoading} = useUser();

    return (
        <div
            className="bg-zinc-800/50 backdrop-blur-md rounded-xl shadow px-6 py-4 mx-4 mt-4 max-w-7xl w-full self-center">
            <div className="p-4">
                <UserProfileHeaderComponent/>
                <UserProfileInfoComponent user={user} isLoading={isLoading}/>
            </div>
        </div>
    );
};

export default UserProfilePage;
