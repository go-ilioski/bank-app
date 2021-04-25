import React from 'react';
import {getAccountTransactions} from "../../service/transactionService";
import {Card, Pagination, Table} from "react-bootstrap";
import Loader from "../Loader/Loader";

class Account extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            transactions: undefined,
            transactionsCount: undefined,
            page: 0,
            size: 5,
            sort: 'createdDate,amount,desc',
            searchStartDate: '15-02-2021',
            searchEndDate: '25-04-2021'
        };
    }

    componentDidMount() {
        this.loadTransactions();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevState.page !== this.state.page) {
            this.loadTransactions();
        }
    }

    handlePagination(page) {
        this.setState({page})
    }

    loadTransactions = () => {
        const {accountId} = this.props;
        const {
            page,
            size,
            sort,
            searchStartDate,
            searchEndDate
        } = this.state;
        getAccountTransactions(
            accountId,
            page,
            size,
            sort,
            searchStartDate,
            searchEndDate
        ).then((response) => {
            this.setState({
                loading: false,
                transactions: response.data,
                transactionsCount: response.headers['x-total-count']
            })
        })
    }

    renderPaginationItems = () => {
        const {page, size, transactionsCount} = this.state
        const pageCount = Math.ceil(transactionsCount / size);
        const startPage = page - 2 > 0 ?
            page - 2 :
            1;
        const endPage = page + 2 <= pageCount ?
            page + 2 :
            pageCount;

        let items = [];
        for (let i = startPage - 1; i <= endPage-1; i++) {
            items.push(
                <Pagination.Item
                    key={`pagination-${i}`}
                    active={page === i}
                    onClick={() => this.handlePagination(i)}
                >{i + 1}</Pagination.Item>
            )
        }
        return items;
    }

    renderTransactionTableRows = () => {
        const {transactions} = this.state;

        return transactions.map(transaction => {
            return (
                <tr key={`transaction-${transaction.id}`}>
                    <td>{transaction.amount.toFixed(2)}</td>
                    <td>{transaction.commission.toFixed(2)}</td>
                    <td>{transaction.currency}</td>
                    <td>{transaction.createdDate}</td>
                    <td>{transaction.description}</td>
                    <td>{transaction.type}</td>
                    <td>{transaction.fromAccountOwnerEmail}</td>
                    <td>{transaction.toAccountOwnerEmail}</td>
                    <td>{transaction.status}</td>
                </tr>
            );
        });
    }

    renderTransactionTable = () => {
        const {transactions} = this.state;
        return transactions && transactions.length-1 ?
            (
                <>
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Amount</th>
                            <th>Commission</th>
                            <th>Currency</th>
                            <th>On</th>
                            <th>Description</th>
                            <th>Type</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.renderTransactionTableRows()}
                        </tbody>
                    </Table>
                    <Pagination>
                        {/*<Pagination.First/>*/}
                        {/*<Pagination.Prev/>*/}
                        {this.renderPaginationItems()}
                        {/*<Pagination.Next/>*/}
                        {/*<Pagination.Last/>*/}
                    </Pagination>
                </>
            ) :
            (
                <p>
                    There are not transactions to show.
                </p>
            )
    }

    render() {
        console.log(this.state.transactionsCount);
        const {loading} = this.state;
        return (
            <Card>
                <Card.Body>
                    <Card.Title>
                        Transactions
                    </Card.Title>
                    <Loader loading={loading} render={this.renderTransactionTable}/>
                </Card.Body>
            </Card>
        );
    }

}

export default Account;