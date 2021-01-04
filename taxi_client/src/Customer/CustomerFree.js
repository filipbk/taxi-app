import React, {Component} from "react";
import {authenticationService, OrdersHistory, PositionAutocomplete} from "../utils";
import {Autocomplete} from '@material-ui/lab'
import {Button, TextField} from "@material-ui/core";

class CustomerFree extends Component {
    constructor(props) {
        super(props);

        this.state = {
            cities: null,
            city: null,
            srcLat: null,
            srcLng: null,
            dstLat: null,
            dstLng: null,
            src: null,
            dst: null
        };

        this.changeStartPoint = this.changeStartPoint.bind(this);
        this.changeEndPoint = this.changeEndPoint.bind(this);
        this.sendData = this.sendData.bind(this);
    }

    render() {
        const {cities} = this.state;

        return(
            <React.Fragment>
                <Autocomplete
                    style={{marginBottom: 10}}
                    onChange={(event, city) => this.setState({city})}
                    id="city"
                    options={cities}
                    getOptionLabel={option => option}
                    renderInput={params => (
                        <TextField {...params} variant="outlined" fullWidth label="Wybierz miasto"
                                   />
                    )}
                />
                <PositionAutocomplete style={{marginBottom: 10}} placeholder={"Wybierz swoja lokalizację"} callback={this.changeStartPoint}/>
                <PositionAutocomplete style={{marginBottom: 10}} placeholder={"Wybierz cel trasy"} callback={this.changeEndPoint}/>
                <Button
                    style={{marginBottom: 20, marginTop: 20}}
                    variant="contained"
                    color="primary"
                    fullWidth
                    onClick={this.sendData}
                >
                    Pokaż dostępnych kierowców
                </Button>

                <OrdersHistory type='CUSTOMER' />
            </React.Fragment>
        );
    }

    componentDidMount() {
        this.fetchCities();
    }

    changeStartPoint(lat, lng, address) {
        this.setState({
            srcLat: lat,
            srcLng: lng,
            src: address
        });
    }

    changeEndPoint(lat, lng, address) {
        this.setState({
            dstLat: lat,
            dstLng: lng,
            dst: address
        });
    }

    sendData() {
        const request = this.state;

        authenticationService.request('/customer/available_drivers', 'POST', request)
            .then(result => {
                console.log(result);
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    fetchCities() {
        authenticationService.request('/customer/cities', 'GET')
            .then(cities => {
                this.setState({
                    cities: cities
                });
            })
            .catch(error => console.log(error));
    }
}

export default CustomerFree;