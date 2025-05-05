import React from "react";
import UserContext from "./UserContext.tsx";
import {formatCurrency} from "../../utils/currencyUtils.ts";
import {useGetMe} from "../../api/user.api.ts";
import {formatDate, formatDateTime} from "../../utils/dateUtils.ts";

const UserProvider: React.FC<{ children: React.ReactNode }> = ({children}) => {
    const {data, isLoading, isError, refetch} = useGetMe();
    const displayCurrency = (val: string | number) => {
        return formatCurrency(val, data?.countryLocale, data?.countryCurrency);
    }
    const displayDate = (date: string) => {
        return formatDate(date, data?.countryLocale);
    }
    const displayDateTime = (date: string) => {
        return formatDateTime(date, data?.countryLocale);
    }
    return (
        <UserContext.Provider
            value={{user: data, isLoading, isError, refetch, displayCurrency, displayDate, displayDateTime}}>
            {children}
        </UserContext.Provider>
    );
};

export default UserProvider;