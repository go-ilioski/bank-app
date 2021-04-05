import axios from "./http";

const apiUri = "/api/users";

export const getAuthenticatedUser = () => {
    return axios.get(`${apiUri}/authenticated`);
};