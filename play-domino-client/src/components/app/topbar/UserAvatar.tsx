import {HiCheckCircle} from "react-icons/hi";
import {Link} from "react-router-dom";
import {Skeleton} from "../../generic/Skeleton.tsx";
import {useUser} from "../../../providers/user/useUser.tsx";

const UserAvatar = () => {
    const {user, isLoading} = useUser();
    if (isLoading || !user) {
        return (<Skeleton className="w-9 h-9"/>)
    }
    const initial = user.name.charAt(0).toUpperCase();

    return (
        <div className="flex items-center gap-2">
            <Link to="/app/profile">
                <div
                    className="w-9 h-9 rounded-full bg-[#fdeccd] text-zinc-900 flex items-center justify-center font-bold">
                    {initial}
                </div>
            </Link>
            <div>
                <h1 className="text-[#fdeccd] font-semibold text-sm leading-none">{user.name}</h1>
                <span className="text-green-400 text-xs flex items-center gap-1">
                    <HiCheckCircle className="inline-block"/> Online
                </span>
            </div>
        </div>
    );
};

export default UserAvatar;
