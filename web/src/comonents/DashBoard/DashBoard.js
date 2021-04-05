import React from "react";
import {withAuthContext} from "../../context/AuthContext";

class DashBoard extends React.Component {

    render() {
        const {isAuthenticated} = this.props;
        console.log(isAuthenticated, "TEST AUTHENTICATEd")
        return (
            <div>
                {isAuthenticated}
                smaki e based
            </div>
        );
    }
}

export default withAuthContext(DashBoard);