import React from "react";
import {Button, Container, Form} from "react-bootstrap";
import {createAccount} from "../../service/accountService";
import {withRouter} from 'react-router-dom';

class CreateAccount extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currency: "MKD",
            error: undefined
        }
    }

    render() {
        const {error} = this.state;
        return (
          <Container>
              <Form>
                  <Form.Group>
                      <Form.Label>Account Currency</Form.Label>
                      <Form.Control
                          as="select"
                          name="currency"
                          onChange={this.handleChange}
                      >
                          <option value="MKD">Macedonian denar - MKD</option>
                          <option value="EUR">Euro - EUR</option>
                      </Form.Control>
                  </Form.Group>

                  {
                      error ?
                          <div className='error'>
                              {error}
                          </div> :
                          ""
                  }
                  <Button variant="primary" type="button" onClick={this.handleSubmit}>
                      Create Account
                  </Button>
              </Form>
          </Container>
        );
    }

    handleSubmit = () => {
        const {history} = this.props;
        const {currency} = this.state;

        const account = {
            currency: currency
        }

        // TODO
        createAccount(account)
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

export default withRouter(CreateAccount);