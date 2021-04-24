import axios from "./http";

const apiUri = "/api/users";

export const getAuthenticatedUser = () => {
    return axios.get(`${apiUri}/authenticated`);
};

export const register = (user) => {
    return axios.post(`${apiUri}/register`, user);
}

export const getUserAccounts = () => {
    return axios.get(`${apiUri}/accounts`);
}