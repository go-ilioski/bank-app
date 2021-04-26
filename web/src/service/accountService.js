import axios from "./http";

const apiUri = "/api/account";

export const createAccount = (account) => {
    return axios.post(`${apiUri}/create`, account);
};

export const getAccount = (accountId) => {
    return axios.get(`${apiUri}/${accountId}`);
};
