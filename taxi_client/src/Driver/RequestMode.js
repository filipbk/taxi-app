import React, {Component} from "react";
import {Button, CircularProgress, Typography} from "@material-ui/core";
import {authenticationService} from "../utils";
import Grid from "@material-ui/core/Grid";
import GoogleMapReact from "google-map-react";
import LocationOnIcon from '@material-ui/icons/LocationOn';
import {deepPurple, green, red} from "@material-ui/core/colors";



class RequestMode extends Component {
    constructor(props) {
        super(props);

        this.state = {
            order: null,
            location: null,
        };

        this.acceptOrder = this.acceptOrder.bind(this);
        this.rejectOrder = this.rejectOrder.bind(this);
        this.getLocation = this.getLocation.bind(this);
    }

    componentDidMount() {
        this.fetchOrderData();
        this.getLocation();
    }

    render() {
        const {order, location} = this.state;

        if(!order || !location) {
            return <CircularProgress/>
        }

        const center = {
            lat: location.lat,
            lng: location.lng
        };

        return(
            <React.Fragment>
                <Typography variant="h4">
                    <b>Masz nowe zgłoszenie z:</b> {order.src} <br/> <b>do: </b> {order.dst}
                </Typography>
                <Typography variant="h5">
                    <b>Klient:</b> {order.customer.name + ' ' + order.customer.surname}<br/>
                    <b>Email:</b> {order.customer.email}<br/>
                    <b>Numer telefonu:</b> {order.customer.phoneNumber}<br/>
                    <b>Dlugość trasy:</b> {order.distance}<br/>
                    <b>Cena:</b> {order.price} PLN
                </Typography>

                <Grid container spacing={3}>
                    <Grid item xs={12} md={6}>
                        <Button
                            variant="contained"
                            color="primary"
                            fullWidth
                            onClick={this.acceptOrder}
                        >
                            Zaakceptuj
                        </Button>
                    </Grid>
                    <Grid item xs={12} md={6}>
                        <Button
                            variant="contained"
                            color="secondary"
                            fullWidth
                            onClick={this.rejectOrder}
                        >
                            Odrzuć
                        </Button>
                    </Grid>
                </Grid>

                <div style={{ height: '100vh', width: '100%' }}>
                    <GoogleMapReact
                        bootstrapURLKeys={{ key: 'google_api_key' }}
                        defaultCenter={center}
                        defaultZoom={13}
                    >
                        <LocationOnIcon
                            style={{color: deepPurple[500]}}
                            lat={center.lat}
                            lng={center.lng}
                            text="My Marker"
                        />
                        <LocationOnIcon
                            style={{color: green[500]}}
                            lat={order.startPoint.lat}
                            lng={order.startPoint.lng}
                            text="My Marker"
                        />
                        <LocationOnIcon
                            style={{color: red[500]}}
                            lat={order.destinationPoint.lat}
                            lng={order.destinationPoint.lng}
                            text="My Marker"
                        />
                    </GoogleMapReact>
                </div>
            </React.Fragment>
        )
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

    fetchOrderData() {
        authenticationService.request('/ride/waiting_order', 'GET')
            .then(order => {
                this.setState({
                    order: order.data
                });

                console.log(order);
            })
            .catch(error => console.log(error));
    }

    rejectOrder() {
        authenticationService.request('/ride/reject_order', 'GET')
            .then(result => {
                this.setState({
                    redirect: true
                });
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    acceptOrder() {
        authenticationService.request('/ride/accept_order', 'GET')
            .then(result => {
                this.setState({
                    redirect: true
                });
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }
}

export default RequestMode;