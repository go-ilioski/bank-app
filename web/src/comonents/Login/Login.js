import React from "react";
import {Button, Container, Form} from "react-bootstrap";
import {withAuthContext} from "../../context/AuthContext";
import './Login.css';
import {withRouter} from "react-router-dom";

class Login extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: "",
            error: false
        }
    }
    render() {
        const {error} = this.state;
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

                  {
                      error ?
                          <div className='error'>
                              Wrong credentials
                          </div> :
                          ""
                  }
                  <Button variant="primary" type="button" onClick={this.handleSubmit}>
                      Login
                  </Button>
              </Form>
          </Container>
        );
    }

    handleSubmit = () => {
        const {login, history} = this.props;
        const {email, password} = this.state;

        const credentials = {
            email: email,
            password: password
        }

        login(credentials)
            .then(res => {
                history.push('/');
            })
            .catch(ex => {
                this.setState({error: true});
            });
    }

    handleChange = (event) => {
        let newState = {error: false};
        newState[event.target.name] = event.target.value;

        this.setState(newState)
    }
}

export default withRouter(withAuthContext(Login));