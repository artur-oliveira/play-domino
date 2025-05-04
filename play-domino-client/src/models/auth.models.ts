
export type RegisterPayload = {
    federalDocument: string;
    country: 'BRAZIL';
    username: string;
    firstname: string;
    lastname: string;
    email: string;
    password: string;
};

export type LoginPayload = {
    email: string;
    password: string;
}

export type LoginResponse = {
    accessToken: string;
    refreshToken: string;
}