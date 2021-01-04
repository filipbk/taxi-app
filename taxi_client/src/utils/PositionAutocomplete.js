import React, {Component} from "react";
import {TextField} from "@material-ui/core";
import Script from 'react-load-script';


class PositionAutocomplete extends Component {
    constructor(props) {
        super(props);
        this.autocompleteInput = React.createRef();
        this.autocomplete = null;
        this.handlePlaceChanged = this.handlePlaceChanged.bind(this);
        this.initAutocomplete = this.initAutocomplete.bind(this);

        this.state = {
            loaded: false,
        }
    }

    initAutocomplete() {
        this.setState({
            loaded: true
        });

        this.autocomplete = new window.google.maps.places.Autocomplete(
            this.autocompleteInput.current,
            {
                types: ["geocode"],
                componentRestrictions: {country: 'pl'}
            }
        );

        this.autocomplete.addListener('place_changed', this.handlePlaceChanged);
    }

    handlePlaceChanged(){
        const place = this.autocomplete.getPlace();
        const address = place.formatted_address;
        const lat = place.geometry.location.lat();
        const lng = place.geometry.location.lng();
        this.props.callback(lat, lng, address);
    }



    render() {
        const {placeholder} = this.props;
        const {loaded} = this.state;

        return (
            <React.Fragment>
                <Script
                    url="https://maps.googleapis.com/maps/api/js?key=google_api_key&libraries=places&language=pl&region=PL"
                    onError={error => console.log(error)}
                    onLoad={this.initAutocomplete}
                />
                {loaded ?
                    <TextField inputRef={this.autocompleteInput}  placeholder={placeholder} fullWidth
                               margin="dense"
                               type="text"></TextField> : null}
            </React.Fragment>
        );
    }
}

export default PositionAutocomplete;