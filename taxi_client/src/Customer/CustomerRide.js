import React, {Component} from "react";
import {authenticationService} from "../utils";
import {
    CircularProgress,
    Typography
} from "@material-ui/core";

class CustomerRide extends Component {
    constructor(props) {
        super(props);

        this.state = {
            ride: null,
        };
    }

    render() {
        const {ride} = this.state;
        const {status} = this.props;

        if(!ride) {
            return <CircularProgress />
        }

        let message;

        if(status === 'WAITING_FOR_DRIVER_ACCEPT') {
            message = 'Oczekiwanie na akceptację zlecenia przez kierowcę';
        } else if (status === 'WAITING') {
            message = 'Kierowca zaakceptował zgłoszenie, oczekuj na jego przyjazd';
        } else if (status === 'DRIVER_ARRIVED') {
            message = 'Kierowca już przyjechał i czeka na Ciebie';
        } else {
            message = 'Przejazd w trakcie';
        }

        return (
            <React.Fragment>
                <Typography variant='h4'><b>{message}</b></Typography>
                <Typography variant="h5">
                    <b>Miejsce początkowe :</b> {ride.src} <br/>
                    <b>Miejsce docelowe: </b> {ride.dst}<br/>
                    <b>Dlugość trasy: </b> {ride.distance} <br/>
                    <b>Kierowca:</b> {ride.dailyInfo.driver.name + ' ' + ride.dailyInfo.driver.surname} <br/>
                    <b>Numer telefonu kierowcy:</b> {ride.dailyInfo.driver.phoneNumber} <br/>
                    <b>Email kierowcy:</b> {ride.dailyInfo.driver.email} <br/>
                    <b>Samochód:</b> {ride.dailyInfo.car.color + ' ' + ride.dailyInfo.car.producer + ' ' + ride.dailyInfo.car.modelName + ' ' + ride.dailyInfo.car.productionYear} <br/>
                    <b>Numer rejestracyjny:</b> {ride.dailyInfo.car.registrationNumber} <br/>
                </Typography>
            </React.Fragment>
            /*<React.Fragment>
                <Grid container alignItems="flex-start" justify="space-between" direction="row">
                    <Typography variant="h5">
                        <React.Fragment><b>Podróż z :</b> {request.src} <br/> <b>do: </b> {request.dst}</React.Fragment> <br/>
                        <b>Dlugość trasy: </b> {drivers.distanceMatrix.rows[0].elements[0].distance.humanReadable}
                    </Typography>
                    <Button
                        onClick={this.cancel}
                        style={{justifyContent: 'right'}}
                        variant="outlined"
                        color="primary">
                        Anuluj
                    </Button>
                </Grid>
            </React.Fragment>*/
        )
    }

    componentDidMount() {
        this.fetchRideData();
    }

    fetchRideData() {
        authenticationService.request('/ride/current_request', 'GET')
            .then(ride => {
                this.setState({
                    ride: ride.data
                });
            })
            .catch(error => console.log(error));
    }
}

export default CustomerRide;