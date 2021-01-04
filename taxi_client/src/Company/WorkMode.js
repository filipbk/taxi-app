import React, {Component} from "react";
import {authenticationService} from "../utils";
import {
    Button,
    Typography,
} from "@material-ui/core";

class WorkMode extends Component {
    positionInterval = null;
    updateInterval = null;

    constructor(props) {
        super(props);

        this.state = {
            user: authenticationService.currentUserValue,
            status: null,
            cars: null,
            location: null,
            position: null,
            redirect: false
        }

        this.endDriving = this.endDriving.bind(this);
        this.getLocation = this.getLocation.bind(this);
        this.updatePosition = this.updatePosition.bind(this);
    }

    componentDidMount() {
        //this.fetchStatus();
        this.getLocation();
        this.positionInterval = setInterval(this.getLocation, 10000);
        this.updateInterval = setInterval(this.updatePosition, 10000);
    }

    componentWillUnmount() {
        clearInterval(this.positionInterval);
        clearInterval(this.updateInterval);
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

    updatePosition() {
        const {location} = this.state;
        const request = {
            lat: location.lat,
            lng: location.lng
        };

        authenticationService.request('/driver/update_position', 'POST', request)
            .then(result => {
                console.log(result);
                this.setState({
                    redirect: true
                });
            })
            .catch(error => console.log(error));
    }

    render() {

        return(
            <React.Fragment>
                <Typography variant="h5" m="16px">
                    Oczekiwanie na zgłoszenia klientów
                </Typography>
                <Button variant="contained"
                        color="primary"
                        onClick={this.endDriving}>
                    Zakończ jazdę
                </Button>
            </React.Fragment>
        )
    }

    fetchStatus() {
        authenticationService.request('/auth/status', 'GET')
            .then(status => {
                this.setState({
                    status: status
                })
            })
            .catch(error => console.log(error));
    }

    endDriving(e) {
        e.preventDefault();
        authenticationService.request('/driver/end_driving', 'GET')
            .then(result => {
                this.setState({
                    redirect: true
                });
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

}

export default WorkMode;