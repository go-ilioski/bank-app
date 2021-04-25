import React from 'react';
import {Card, Table} from "react-bootstrap";
import Loader from "../Loader/Loader";
import {getFavorites} from "../../service/userService";

class Favorites extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            favorites: undefined
        };
    }

    componentDidMount() {
        this.loadFavorites();
    }

    loadFavorites = () => {

        getFavorites(
        ).then((response) => {
            this.setState({
                loading: false,
                favorites: response.data
            })
        })
    }

    renderFavoritesTableRows = () => {
        const {favorites} = this.state;

        return favorites.map(favorite => {
            return (
                <tr key={`favorite-${favorite.id}`}>
                    <td>{favorite.firstName}</td>
                    <td>{favorite.lastName}</td>
                    <td>{favorite.email}</td>
                </tr>
            );
        });
    }

    renderFavoriteTable = () => {
        const {favorites} = this.state;
        return favorites ?
            (
                <>
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email</th>

                        </tr>
                        </thead>
                        <tbody>
                        {this.renderFavoritesTableRows()}
                        </tbody>
                    </Table>
                </>
            ) :
            (
                <p>
                    There are no favorite to show.
                </p>
            )
    }

    render() {
        const {loading} = this.state;
        return (
            <Card>
                <Card.Body>
                    <Card.Title>
                        Favorites
                    </Card.Title>
                    <Loader loading={loading} render={this.renderFavoriteTable()}/>
                </Card.Body>
            </Card>
        );
    }

}

export default Favorites;