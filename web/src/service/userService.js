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

export const getFavorites = () => {
    return axios.get(`${apiUri}/favorites`);
}

export const addFavorite = (id) => {
    return axios.post(`${apiUri}/favorites`, id);
}

export const removeFavorite = (id) => {
    return axios.delete(`${apiUri}/favorites/${id}`, id);
}