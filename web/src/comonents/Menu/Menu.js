import React from "react";
import {Button, Form, FormControl, Nav, Navbar} from "react-bootstrap";
import {withAuthContext} from "../../context/AuthContext";

class Menu extends React.Component{

    handleLogout = () => {
        const {logout} = this.props;
        logout();
    }

    render() {
        return (
            <>
                <Navbar bg="dark" variant="dark">
                    <Navbar.Brand href="#home">Navbar</Navbar.Brand>
                    <Nav className="mr-auto">
                        <Nav.Link href="#home">Home</Nav.Link>
                        <Nav.Link href="#features">Features</Nav.Link>
                        <Nav.Link href="#pricing">Pricing</Nav.Link>
                    </Nav>
                    <Form inline>
                        <FormControl type="text" placeholder="Search" className="mr-sm-2" />
                        <Button variant="outline-info">Search</Button>
                        <Button variant="danger" onClick={this.handleLogout}>Log Out</Button>
                    </Form>
                </Navbar>
            </>
        );
    }

}

export default withAuthContext(Menu);
