import Logo from "../../generic/Logo.tsx";
import UserAvatar from "./UserAvatar.tsx";
import {Link} from "react-router-dom";
import {useAuth} from "../../../providers/auth/useAuth.tsx";

const TopBarLeftSide = () => {
    const authenticated = useAuth().isAuthenticated();

    return (<div className="flex items-center gap-4">
        <Link to={authenticated ? '/app' : '/'}>
            <Logo className="h-8 w-8 text-[#fdeccd]"/>
        </Link>
        {authenticated && <UserAvatar/>}
    </div>)
};

export default TopBarLeftSide;
