import React from "react";
import {useGetMe} from "../../api/user/useGetMe.ts";
import UserContext from "./UserContext.tsx";
import {formatCurrency} from "../../utils/currencyUtils.ts";

const UserProvider: React.FC<{ children: React.ReactNode }> = ({children}) => {
    const {data, isLoading, isError, refetch} = useGetMe();
    const displayCurrency = (val: string | number) => {
        return formatCurrency(val, data?.countryLocale, data?.countryCurrency);
    }
    return (
        <UserContext.Provider value={{user: data, isLoading, isError, refetch, displayCurrency}}>
            {children}
        </UserContext.Provider>
    );
};

export default UserProvider;