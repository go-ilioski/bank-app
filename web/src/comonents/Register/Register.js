import React from "react";
import {Button, Container, Form} from "react-bootstrap";
import {withAuthContext} from "../../context/AuthContext";
import './Register.css';
import {register} from "../../service/userService";
import { withRouter } from 'react-router-dom';

class Register extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            confirmPassword: "",
            error: undefined
        }
    }

    render() {
        const {error} = this.state;
        return (
          <Container>
              <h1>Register</h1>
              <Form>
                  <Form.Group>
                      <Form.Label>First Name</Form.Label>
                      <Form.Control
                          type="text"
                          placeholder="First Name"
                          name="firstName"
                          onChange={this.handleChange}/>
                  </Form.Group>

                  <Form.Group>
                      <Form.Label>Last Name</Form.Label>
                      <Form.Control
                          type="text"
                          placeholder="Last Name"
                          name="lastName"
                          onChange={this.handleChange}/>
                  </Form.Group>

                  <Form.Group>
                      <Form.Label>Email address</Form.Label>
                      <Form.Control
                          type="email"
                          placeholder="Enter email"
                          name="email"
                          onChange={this.handleChange}/>
                  </Form.Group>

                  <Form.Group>
                      <Form.Label>Password</Form.Label>
                      <Form.Control
                          type="password"
                          placeholder="Password"
                          onChange={this.handleChange}
                          name="password"
                      />
                  </Form.Group>
                  <Form.Group>
                      <Form.Label>Confirm Password</Form.Label>
                      <Form.Control
                          type="password"
                          placeholder="Confirm Password"
                          onChange={this.handleChange}
                          name="confirmPassword"
                      />
                  </Form.Group>

                  {
                      error ?
                          <div className='error'>
                              {error}
                          </div> :
                          ""
                  }
                  <Button variant="primary" type="button" onClick={this.handleSubmit}>
                      Register
                  </Button>
              </Form>
          </Container>
        );
    }

    handleSubmit = () => {
        const {history} = this.props;
        const {email, password, firstName, lastName, confirmPassword} = this.state;

        if (password !== confirmPassword) {
            this.setState({error: "Passwords do not match"});
            return;
        }

        const user = {
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName,
            role: 'user'
        }

        register(user)
            .then(res => {
                history.push('/login');
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

export default withRouter(withAuthContext(Register));