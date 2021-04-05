import React from "react";
import {Button, Container, Form} from "react-bootstrap";
import {withAuthContext} from "../../context/AuthContext";

class Login extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: ""
        }
    }
    render() {
        // console.log(this.state);
        return (
          <Container>
              <Form>
                  <Form.Group controlId="formBasicEmail">
                      <Form.Label>Email address</Form.Label>
                      <Form.Control
                          type="email"
                          placeholder="Enter email"
                          name="email"
                          onChange={this.handleChange}/>
                  </Form.Group>

                  <Form.Group controlId="formBasicPassword">
                      <Form.Label>Password</Form.Label>
                      <Form.Control
                          type="password"
                          placeholder="Password"
                          onChange={this.handleChange}
                          name="password"
                      />
                  </Form.Group>

                  <Button variant="primary" type="button" onClick={this.handleSubmit}>
                      Login
                  </Button>
              </Form>
          </Container>
        );
    }

    handleSubmit = () => {
        const {login} = this.props;
        const {email, password} = this.state;

        const credentials = {
            email: email,
            password: password
        }

        login(credentials)
            .then(res => {console.log(res, "LOGGED IN")})
            .catch(ex => {console.log(ex, "FAILED LOG IN")});
    }

    handleChange = (event) => {
        let newState = {};
        newState[event.target.name] = event.target.value;

        this.setState(newState)
    }
}

export default withAuthContext(Login);