import React from "react";
import {Button, Container, Form} from "react-bootstrap";
import {createAccount} from "../../service/accountService";

class CreateAccount extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            currency: "",
            error: undefined
        }
    }

    render() {
        const {error} = this.state;
        return (
          <Container>
              <Form>
                  <Form.Group>
                      <Form.Label>Choose Currency</Form.Label>
                      {/*<Form.Control*/}
                      {/*    type="text"*/}
                      {/*    placeholder="First Name"*/}
                      {/*    name="currency"*/}
                      {/*    onChange={this.handleChange}/>*/}
                      <Form.Control name="currency" onChange={this.handleChange} >
                          <option>USD</option>
                          <option>MKD</option>
                          <option>EUR</option>
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
                history.push('/account/res.id');
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

export default CreateAccount;