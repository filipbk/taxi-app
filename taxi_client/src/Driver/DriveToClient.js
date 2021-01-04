import React, {Component} from "react";
import {Button, CircularProgress, Typography} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import {authenticationService} from "../utils";

class DriveToClient extends Component {
    constructor(props) {
        super(props);

        this.state = {
            order: null,
            position: null,
            location: null
        };

        this.driverArrived = this.driverArrived.bind(this);
        this.startRide = this.startRide.bind(this);
        this.endRide = this.endRide.bind(this);
    }

    componentDidMount() {
        this.fetchOrderData();
        this.getLocation();
    }

    render() {
        const {order, location} = this.state;
        const {status} = this.props;

        if(!order) {
            return <CircularProgress/>
        }

        //const MapLoader = withScriptjs(DirectionsMap);

        if(!location || !order) {
            return <CircularProgress/>
        }

        const navMode = status === 'NAVIGATION' ? true : false;
        const navigationUrl = navMode ? 'https://www.google.com/maps/dir/?api=1&origin=' + location.lat + ',' + location.lng + '&destination=' + order.src  + '&travelmode=driving' :
            'https://www.google.com/maps/dir/?api=1&origin=' + location.lat + ',' + location.lng + '&destination=' + order.dst  + '&travelmode=driving';
        const mapUrl = navMode ? 'https://www.google.com/maps/embed/v1/directions?origin=' + location.lat + ',' + location.lng + '&destination=' + order.src  + '&key=google_api_key' :
            'https://www.google.com/maps/embed/v1/directions?origin=' + location.lat + ',' + location.lng + '&destination=' + order.dst  + '&key=google_api_key';

        const buttonSize = navMode ? 4 : 6;

        return(
            <React.Fragment>
                <Typography variant="h4">
                    {
                        navMode ?
                        <React.Fragment><b>Jedź do klienta do:</b> {order.src}</React.Fragment>
                        :
                        <React.Fragment><b>Jedź do celu podróży:</b> {order.dst}</React.Fragment>
                    }

                </Typography>
                <b>Klient:</b> {order.customer.name + ' ' + order.customer.surname}<br/>
                <b>Email:</b> {order.customer.email}<br/>
                <b>Numer telefonu:</b> {order.customer.phoneNumber}<br/>

                <Grid container spacing={3}>
                    <Grid item xs={12} md={buttonSize}>
                        <Button
                            variant="contained"
                            color="primary"
                            fullWidth
                            onClick={() => window.open(navigationUrl, "_blank")}
                        >
                            Nawiguj
                        </Button>
                    </Grid>

                    {navMode ?
                    <Grid item xs={12} md={buttonSize}>
                        <Button
                            variant="contained"
                            color="secondary"
                            fullWidth
                            onClick={this.driverArrived}
                        >
                            Zgłoś przyjazd do klienta
                        </Button>
                    </Grid> : null}

                    <Grid item xs={12} md={buttonSize}>
                        <Button
                            variant="contained"
                            color="primary"
                            fullWidth
                            onClick={navMode ? this.startRide : this.endRide}
                        >
                            {navMode ? 'Rozpocznij przejazd' : 'Zakończ przejazd'}
                        </Button>
                    </Grid>
                </Grid>

                <iframe width="100%" height="550" title="xxx"
                        src={mapUrl}
                        allowFullScreen></iframe>
            </React.Fragment>
        )
    }

    fetchOrderData() {
        authenticationService.request('/ride/current_order', 'GET')
            .then(order => {
                this.setState({
                    order: order.data
                })
            })
            .catch(error => console.log(error));
    }

    driverArrived() {
        authenticationService.request('/ride/driver_arrived', 'GET')
            .then(result => {
                this.setState({
                    redirect: true
                });
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    startRide() {
        authenticationService.request('/ride/start_ride', 'GET')
            .then(result => {
                this.setState({
                    redirect: true
                });
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    endRide() {
        authenticationService.request('/ride/end_ride', 'GET')
            .then(result => {
                this.setState({
                    redirect: true
                });
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    getLocation() {
        let errorOccurred = false;

        navigator.geolocation.getCurrentPosition(position => {
            this.setState({
                location: {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                }
            })
        }, error => {
            console.log(error);
            errorOccurred = true;
        });

        if(errorOccurred) {
            authenticationService.mapsApiRequest('geolocation/v1/geolocate', 'POST')
                .then(location => {
                    this.setState({
                        location: location.location
                    });
                })
                .catch(error => console.log(error));
        }
    }
}

export default DriveToClient;