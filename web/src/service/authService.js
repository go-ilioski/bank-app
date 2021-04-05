import axios from "./http";

const apiUri = "/api/authenticate";
export const authenticateUser = (user) => {
    return axios.post(apiUri, user, {withCredentials: true});
};