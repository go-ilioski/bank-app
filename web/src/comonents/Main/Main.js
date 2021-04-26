import React from "react";
import {withAuthContext} from "../../context/AuthContext";
import Menu from "../Menu/Menu";
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import DashBoard from "../DashBoard/DashBoard";
import {Container} from "react-bootstrap";
import Account from "../Account/Account";
import CreateAccount from "../Account/CreateAccount";
import Favorites from "../Favorites/Favorites";
import MakeTransaction from "../MakeTransaction/MakeTransaction";

class Main extends React.Component {

    render() {
        return (
            <div>
                <Menu/>
                <Container>
                    <Router>
                        <Switch>

                            <Route path="/account/create" component={CreateAccount}/>

                            <Route path="/account/:id/make-transaction" render={({match}) => {
                                return (<MakeTransaction accountId={match.params.id} />)
                            }} />

                            <Route path="/account/:id" render={({match}) => {
                                return (<Account accountId={match.params.id} />)
                            }} />


                            {/*<Route path="/account/create" component={CreateAccount}/>*/}
                            <Route path="/favorites" component={Favorites}/>
                            <Route path="/" component={DashBoard}/>
                            {/*<Route path="/account/create" render={() => {*/}
                            {/*    return (<CreateAccount />)*/}
                            {/*}} />*/}

                        </Switch>
                    </Router>
                </Container>
            </div>
        );
    }
}

export default withAuthContext(Main);