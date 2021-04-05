import axios from "axios";
import {clearAccessToken, getAccessToken} from "./browserStorageService";

const authenticationInterceptor = (config) => {
    const accessToken = getAccessToken();

    if (accessToken) {
        config.headers['Authorization'] = accessToken;
    }

    return config;
};

axios.interceptors.request.use(authenticationInterceptor);

const globalErrorHandler = (error) => {

    if (error.response && error.response.status === 401) {
        clearAccessToken();
    }

    return Promise.reject(error);
};

axios.interceptors.response.use(response => response, globalErrorHandler);

export default axios;