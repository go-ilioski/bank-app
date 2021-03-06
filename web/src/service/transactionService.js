import axios from "./http";

const apiUri = "/api/transactions";

export const getAccountTransactions = (
    accountId,
    page,
    size,
    sort,
    searchStartDate,
    searchEndDate,
    startAmount,
    endAmount
    ) => {
    let optionalParamsString = "";
    if (searchStartDate) {
        optionalParamsString += `&searchStartDate=${searchStartDate}`;
    }
    if (searchEndDate) {
        optionalParamsString += `&searchEndDate=${searchEndDate}`;
    }
    if (startAmount) {
        optionalParamsString += `&startAmount=${startAmount}`;
    }
    if (endAmount) {
        optionalParamsString += `&endAmount=${endAmount}`;
    }
    return axios.get(`${apiUri}/${accountId}/search/page?page=${page}&sort=${sort}&size=${size}${optionalParamsString}`);
}

export const getAccountTransactionsReport = (
    accountId,
    searchStartDate,
    searchEndDate,
    startAmount,
    endAmount
) => {
    let optionalParamsString = "";
    if (searchStartDate) {
        optionalParamsString += `&searchStartDate=${searchStartDate}`;
    }
    if (searchEndDate) {
        optionalParamsString += `&searchEndDate=${searchEndDate}`;
    }
    if (startAmount) {
        optionalParamsString += `&startAmount=${startAmount}`;
    }
    if (endAmount) {
        optionalParamsString += `&endAmount=${endAmount}`;
    }
    const config = {
        responseType: 'blob'
    };
    return axios.get(`${apiUri}/${accountId}/search/report?sort=createdDate,desc${optionalParamsString}`, config);
}


export const createTransaction = (transaction) => {
    return axios.post(apiUri,transaction);
}