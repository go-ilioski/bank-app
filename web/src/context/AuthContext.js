import React from "react";
import {authenticateUser} from "../service/authService";
import {clearAccessToken, getAccessToken, setAccessToken} from "../service/browserStorageService";
import {getAuthenticatedUser} from "../service/userService";

const state = {
    login: () => {
    },
    logout: () => {
    },
    isAuthenticated: false,
    loggedInUser: null
};

const AuthContext = React.createContext(state);

export class AuthProvider extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            login: this.login,
            logout: this.logout,
            isAuthenticated: !!getAccessToken(),
            loggedInUser: null
        };
    }

    componentDidMount() {
        if(this.state.isAuthenticated){
            getAuthenticatedUser().then(response => {
                this.setState({
                    loggedInUser: response.data
                });
            });
        }
    }

    render() {
        return (
            <AuthContext.Provider value={this.state}>
                {this.props.children}
            </AuthContext.Provider>
        );
    }

    login = async (user) => {
        return new Promise((resolve, reject) => {
            authenticateUser(user).then(response => {
                const bearer = response.headers.authorization;
                setAccessToken(bearer);
                this.setState({
                    isAuthenticated: true
                });

                getAuthenticatedUser().then(userResponse => {
                    this.setState({
                        loggedInUser: userResponse.data
                    });
                });
                resolve();
            }).catch(reason => {
                console.log(reason);
                reject ();
            });
        });
    };

    logout = () => {
        clearAccessToken();
        this.setState({
            isAuthenticated: false
        });
    }
}

export const AuthConsumer = AuthContext.Consumer;

export const withAuthContext = (Component) => {
    return (topLevelProps) => (
        <AuthConsumer>
            {(authProps) => {
                return <Component {...topLevelProps} {...authProps}/>
            }}
        </AuthConsumer>
    )
};