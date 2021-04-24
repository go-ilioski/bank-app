import React from "react";
import {withAuthContext} from "../../context/AuthContext";
import Menu from "../Menu/Menu";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import DashBoard from "../DashBoard/DashBoard";
import {Container} from "react-bootstrap";
import Account from "../Account/Account";

class Main extends React.Component {

    render() {
        return (
            <div>
                <Menu/>
                <Container>
                    <Router>
                        <Switch>
                            <Route path="/account/:id" render={({match}) => {
                                return (<Account accountId={match.params.id} />)
                            }} />
                            <Route path="/" component={DashBoard}/>
                        </Switch>
                    </Router>
                </Container>
            </div>
        );
    }
}

export default withAuthContext(Main);