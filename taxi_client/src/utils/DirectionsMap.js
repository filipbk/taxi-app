import React, {Component} from "react";
import {
    withGoogleMap,
    GoogleMap,
    DirectionsRenderer
} from 'react-google-maps'

class DirectionsMap extends Component {
    constructor(props) {
        super(props);

        this.state = {
            directions: null
        }
    }

    componentDidMount() {
        const google = window.google;
        const directionsService = new google.maps.DirectionsService();

        const origin = { lat: 40.756795, lng: -73.954298 };
        const destination = { lat: 41.756795, lng: -78.954298 };

        directionsService.route(
            {
                origin: origin,
                destination: destination,
                travelMode: google.maps.TravelMode.DRIVING
            },
            (result, status) => {
                if (status === google.maps.DirectionsStatus.OK) {
                    this.setState({
                        directions: result
                    });
                } else {
                    console.error(`error fetching directions ${result}`);
                }
            }
        );
    }


    render() {
        const GoogleMapExample = withGoogleMap(props => (
            <GoogleMap
                defaultCenter={{ lat: 40.756795, lng: -73.954298 }}
                defaultZoom={13}
            >
                <DirectionsRenderer
                    directions={this.state.directions}
                />
            </GoogleMap>
        ));

        return (
            <div>
                <GoogleMapExample
                    containerElement={<div style={{ height: `500px`, width: "500px" }} />}
                    mapElement={<div style={{ height: `100%` }} />}
                />
            </div>
        );
    }

}

export default DirectionsMap;