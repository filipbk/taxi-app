import React, {Component} from "react";
import {authenticationService} from "../utils";
import {
    CircularProgress,
} from "@material-ui/core";
import {CustomerFree, CustomerRide, CustomerRideOrder} from "./index";

class CustomerHome extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: authenticationService.currentUserValue,
            status: null
        }
    }

    render() {
        const {status} = this.props;

        if(!status) {
            return <CircularProgress />
        }

        if(status === 'FREE') {
            return <CustomerFree />
        } else if(status === 'RIDE_ORDER_MODE' || status === 'DRIVER_REJECTED') {
            return <CustomerRideOrder />
        } else {
            return <CustomerRide status={status} />
        }
    }

    componentDidMount() {
        this.fetchStatus();
    }

    fetchStatus() {
        authenticationService.request('/auth/status', 'GET')
            .then(status => {
                this.setState({
                    status: status.data
                });
                console.log(status)
            })
            .catch(error => console.log(error));
    }

    rejectRequest(requestId) {
        authenticationService.request('/company/request/reject/' + requestId, 'GET')
            .then(cars => {
                /*this.setState({
                    cars: cars.data.cars
                })*/
                console.log(cars);
            })
            .catch(error => console.log(error));
    }
}

export default CustomerHome;