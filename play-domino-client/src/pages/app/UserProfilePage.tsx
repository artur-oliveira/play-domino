import UserProfileHeaderComponent from "../../components/profile/UserProfileHeaderComponent.tsx";
import UserProfileInfoComponent from "../../components/profile/UserProfileInfoComponent.tsx";
import {useGetMe} from "../../api/user/useGetMe.ts";

const UserProfilePage = () => {
    const {data: user, isLoading} = useGetMe();

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
