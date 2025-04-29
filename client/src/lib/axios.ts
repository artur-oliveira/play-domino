import axios, {HttpStatusCode} from 'axios';

function getAxiosClient() {
    const client = axios.create({
        baseURL: import.meta.env.VITE_API_URL,
    });
    client.interceptors.request.use((config) => {
        const token = localStorage.getItem("authToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    });
    client.interceptors.response.use(
        response => response,
        error => {
            if (error.response?.status === HttpStatusCode.Unauthorized) {
                localStorage.removeItem("authToken");
                window.location.href = '/auth/login';
            }
            return Promise.reject(error);
        }
    );
    return client;
}


export const api = getAxiosClient();
