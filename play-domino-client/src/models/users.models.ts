export type Country = 'BRAZIL';
export type CountryLocale = 'pt-BR';
export type CountryCurrency = 'BRL';

export interface UserUpdate {
    firstname: string | null;
    lastname: string | null;
    federalDocument: string | null;
}

export interface CurrentUser extends UserUpdate {
    id: number;
    email: string;
    username: string;
    createdAt: string;
    name: string;
    country: Country;
    countryLocale: CountryLocale;
    countryCurrency: CountryCurrency;
    enabled: boolean;
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
}