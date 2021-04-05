const tokenKey = "token";

export const setAccessToken = (token) => {
    localStorage.setItem(tokenKey, token);
};

export const getAccessToken = () => {
    return localStorage.getItem(tokenKey);
};

export const clearAccessToken = () => {
    localStorage.removeItem(tokenKey);
};