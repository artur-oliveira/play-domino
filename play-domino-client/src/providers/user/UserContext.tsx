import {createContext} from "react";
import {CurrentUser} from "../../models/users.models";

interface UserContextType {
    user: CurrentUser | undefined;
    isLoading: boolean;
    isError: boolean;
    displayCurrency: (val: string | number) => string;
    displayDate: (val: string) => string;
    displayDateTime: (val: string) => string;
    refetch: () => void;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export default UserContext;

