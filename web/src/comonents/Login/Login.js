import React from "react";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
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
              <h1>Login</h1>
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
                  <Row>
                      <Col>
                          <Button variant="primary" type="button" onClick={this.handleSubmit}>
                              Login
                          </Button>
                      </Col>
                      <Col>
                          <Button variant="success" href="/register">Register</Button>
                      </Col>
                  </Row>

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