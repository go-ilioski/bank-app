import React from "react";
import {Button, Card, Col, Row, Table} from "react-bootstrap";
import Loader from "../Loader/Loader";
import {getUserAccounts} from "../../service/userService";
import {withRouter} from "react-router-dom";

class DashBoard extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            accounts: undefined
        };
    }

    componentDidMount() {
        this.loadUserAccounts();
    }

    loadUserAccounts = () => {
        getUserAccounts()
            .then(result => {
                this.setState({
                    loading: false,
                    accounts: result.data
                })
            })
    }

    renderAccountTableRows = () => {
        const {accounts} = this.state;

        return accounts.map(account => {
            return (
                <tr key={`account-${account.id}`}>
                    <td>{account.currency}</td>
                    <td>{account.balance.toFixed(2)}</td>
                    <td>
                        <Row>
                            <Col>
                                <Button
                                    variant="primary"
                                    onClick={() => {
                                        this.props.history.push(`/account/${account.id}`)
                                    }}
                                >View Transactions</Button>
                            </Col>
                            <Col>
                                <Button
                                    variant="primary"
                                    onClick={() => {
                                        this.props.history.push(`/account/${account.id}/make-transaction`)
                                    }}
                                >Make Transaction
                                </Button>
                            </Col>
                        </Row>
                    </td>
                </tr>
            );
        });
    }

    renderAccountTable = () => {
        const {accounts} = this.state;
        return accounts && accounts.length ?
            (
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>Currency</th>
                        <th>Balance</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.renderAccountTableRows()}
                    </tbody>
                </Table>
            ) :
            (
                <p>
                    Currently you have no accounts.
                </p>
            )
    }

    render() {
        const {loading} = this.state;
        return (
            <Card>
                <Card.Body>
                    <Card.Title>
                        My Accounts
                    </Card.Title>
                    <Loader loading={loading} render={this.renderAccountTable}/>
                    <Button
                        variant="primary"
                        onClick={() => {
                            this.props.history.push(`/account/create`)
                        }}
                    >Create Account</Button>
                </Card.Body>

            </Card>
        );
    }
}

export default withRouter(DashBoard);