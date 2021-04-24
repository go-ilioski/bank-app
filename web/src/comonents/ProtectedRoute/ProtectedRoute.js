import React from 'react';
import {Route, Redirect} from "react-router-dom";
import {withAuthContext} from "../../context/AuthContext";

class ProtectedRoute extends React.Component {

    render() {
        const {isAuthenticated, component: Component, ...rest} = this.props;
        return (
            <Route
                render={() => {
                    if (isAuthenticated) {
                        return <Component />
                    } else {
                        return <Redirect to='/login'/>
                    }
                }}
                {...rest}
            />
        );
    }
}

export default withAuthContext(ProtectedRoute);