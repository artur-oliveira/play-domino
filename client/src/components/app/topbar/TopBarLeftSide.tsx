import Logo from "../../generic/Logo.tsx";
import UserAvatar from "./UserAvatar.tsx";
import {Link} from "react-router-dom";

const TopBarLeftSide = () => (
    <div className="flex items-center gap-4">
        <Link to="/app">
            <Logo className="h-8 w-8 text-[#fdeccd]" />
        </Link>
        <UserAvatar/>
    </div>
);

export default TopBarLeftSide;
