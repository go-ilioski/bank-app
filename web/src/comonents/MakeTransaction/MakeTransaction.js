import React from "react";
import {Button, Container, Form} from "react-bootstrap";
import {getAccount} from "../../service/accountService";
import {createTransaction} from "../../service/transactionService";
import {withRouter} from 'react-router-dom';

class MakeTransaction extends React.Component {
    constructor(props) {
        super(props);


        this.state = {
            account: undefined,
            amount: "",
            type: "WITHDRAW",
            description: "",
            toAccountId: undefined,
            fromAccountId: props.accountId,
            currency: "",
            error: undefined
        }
    }

    componentDidMount() {
        const {accountId} = this.props;

        getAccount(accountId)
            .then(result => {
                this.setState({account: result.data})
            })
    }

    render() {
        console.log(this.state.type)
        const {error} = this.state;
        return (
            <Container>
                <Form>
                    <Form.Group>
                        <Form.Label>Amount</Form.Label>
                        <Form.Control
                            type="number"
                            placeholder="Amount"
                            name="amount"
                            onChange={this.handleChange}/>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Transaction type</Form.Label>
                        <Form.Control
                            as="select"
                            name="type"
                            onChange={(event) => {
                                const {accountId} = this.props;

                                this.handleChange(event);
                                let value = event.target.value;
                                switch (value) {
                                    case "WITHDRAW":
                                        this.setState({fromAccountId: accountId, toAccountId: undefined});
                                        break;
                                    case "DEPOSIT":
                                        this.setState({fromAccountId: undefined, toAccountId: accountId});
                                        break;

                                }
                            }}
                        >
                            <option value="WITHDRAW">WITHDRAW</option>
                            <option value="DEPOSIT">DEPOSIT</option>
                            <option value="PAYMENT">PAYMENT</option>
                        </Form.Control>
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            type="description"
                            placeholder="Enter transaction description"
                            name="description"
                            onChange={this.handleChange}/>
                    </Form.Group>


                    {
                        error ?
                            <div className='error'>
                                {error}
                            </div> :
                            ""
                    }
                    <Button variant="primary" type="button" onClick={this.handleSubmit}>
                        Make Transaction
                    </Button>
                </Form>
            </Container>
        );
    }

    handleSubmit = () => {
        const {history} = this.props;
        const {
            account,
            amount,
            type,
            description,
            toAccountId,
            fromAccountId,
            currency
        } = this.state;


        let transaction = {
            amount,
            type,
            description,
            toAccountId,
            fromAccountId,
            currency: account.currency
        }


        createTransaction(transaction)
            .then(res => {
                history.push('/');
            })
            .catch(ex => {
                console.log(ex, "EXCEPTION")
                this.setState({error: "Something went wrong"});
            });
    }

    handleChange = (event) => {

        let newState = {error: false};
        newState[event.target.name] = event.target.value;

        this.setState(newState)
    }

}

export default withRouter(MakeTransaction); ///