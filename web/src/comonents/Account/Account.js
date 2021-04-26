import React from 'react';
import {getAccountTransactions, getAccountTransactionsReport} from "../../service/transactionService";
import {Button, Card, Col, FormControl, Pagination, Row, Table, Form} from "react-bootstrap";
import Loader from "../Loader/Loader";
import DatePicker from "react-datepicker"
import "react-datepicker/dist/react-datepicker.css"
import "./Account.css"

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
            //searchStartDate: '15-02-2021',
            searchStartDate: undefined,
            //searchEndDate: '28-04-2021'
            searchEndDate: undefined,
            startAmount: undefined,
            endAmount: undefined
        };
    }

    componentDidMount() {
        this.loadTransactions();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {

        const {
            searchStartDate,
            searchEndDate,
            startAmount,
            endAmount
        } = this.state;


        if (prevState.page !== this.state.page ||
            prevState.searchStartDate !== searchStartDate ||
            prevState.searchEndDate !== searchEndDate ||
            prevState.startAmount !== startAmount ||
            prevState.endAmount !== endAmount
        ) {
            this.loadTransactions();
        }

    }

    handlePagination(page) {
        this.setState({page})
    }

    getDateString = (date) =>{
        let dateString = undefined;
        if (date){
            let day = date.getDate().toString()
            let month = date.getMonth() + 1;
            month = month.toString()
            if(day.length === 1){
                day = "0" + day;
            }
            if(month.length === 1){
                month = "0" + month;
            }
            dateString = day + "-" + month + "-" + date.getFullYear();
        }
        return dateString;
    }

    loadTransactions = () => {
        const {accountId} = this.props;
        const {
            page,
            size,
            sort,
            searchStartDate,
            searchEndDate,
            startAmount,
            endAmount
        } = this.state;

        let startDateString = this.getDateString(searchStartDate);
        let endDateString = this.getDateString(searchEndDate);
        console.log(startDateString, endDateString);
        getAccountTransactions(
            accountId,
            page,
            size,
            sort,
            startDateString,
            endDateString,
            startAmount,
            endAmount
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
        for (let i = startPage - 1; i <= endPage - 1; i++) {
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
        return transactions && transactions.length ?
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

    exportReport = () => {
        const {accountId} = this.props;
        const {
            searchStartDate,
            searchEndDate,
            startAmount,
            endAmount
        } = this.state;

        let startDateString = this.getDateString(searchStartDate);
        let endDateString = this.getDateString(searchEndDate);
        getAccountTransactionsReport(
            accountId,
            startDateString,
            endDateString,
            startAmount,
            endAmount
        ).then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', 'transactions.pdf'); //or any other extension
            document.body.appendChild(link);
            link.click();
        })
    }


    render() {
        //console.log(this.state.transactionsCount);
        const {
            loading,
            searchStartDate,
            searchEndDate,
            startAmount,
            endAmount
        } = this.state;

        return (
            <Card>
                <Card.Body>
                    <Card.Title>
                        <h3>Transactions</h3>
                    </Card.Title>
                    <Row>
                        <Col>
                            <Form.Group>
                                <Form.Row>
                                    <Form.Label>
                                        Start Date
                                    </Form.Label>
                                    <DatePicker selected={searchStartDate}
                                                style={{width: "100%"}}
                                                onChange={date => this.setState({searchStartDate: date})}
                                                dateFormat={"dd-MM-yyyy"}
                                                customInput={(<FormControl type="text"/>)}
                                    />
                                </Form.Row>
                            </Form.Group>

                        </Col>

                        <Col>
                            <Form.Group>
                                <Form.Row>
                                    <Form.Label>
                                        End Date
                                    </Form.Label>
                                    <DatePicker selected={searchEndDate}
                                                style={{width: "100%"}}
                                                onChange={date => this.setState({searchEndDate: date})}
                                                dateFormat={"dd-MM-yyyy"}
                                                customInput={(<FormControl type="text"/>)}
                                    />
                                </Form.Row>
                            </Form.Group>
                        </Col>

                        <Col>
                            <Form.Group>
                                <Form.Row>
                                    <Form.Label>
                                        Start Amount
                                    </Form.Label>
                                    <FormControl type="number"
                                                 name="startAmount"
                                                 onChange={(event) => {
                                                     let newState = {};
                                                     newState[event.target.name] = event.target.value;
                                                     this.setState(newState)
                                                 }}
                                    />
                                </Form.Row>
                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group>
                                <Form.Row>
                                    <Form.Label>
                                        End Amount
                                    </Form.Label>
                                    <FormControl type="number"
                                                 name="endAmount"
                                                 onChange={(event) => {
                                                     let newState = {};
                                                     newState[event.target.name] = event.target.value;
                                                     this.setState(newState)
                                                 }}
                                    />
                                </Form.Row>
                            </Form.Group>
                        </Col>

                    </Row>
                    <br/>
                    <Loader loading={loading} render={this.renderTransactionTable}/>
                </Card.Body>


                <Button
                    variant="primary"
                    onClick={() => {
                        this.exportReport()
                    }}
                >Export Jasper Report</Button>
            </Card>
        );
    }

}

export default Account;