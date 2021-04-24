import React from 'react';
import {Spinner} from "react-bootstrap";

class Loader extends React.Component {
    render() {
        const {loading, render} = this.props;

        return loading ? (
                <Spinner animation="border"/>
            ) :
            render();
    }
}

export default Loader;