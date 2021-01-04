import React, {Component} from "react";
import {authenticationService} from "../utils";
import {
    Button,
    CircularProgress,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";

class CustomerRideOrder extends Component {
    constructor(props) {
        super(props);

        this.state = {
            request: null,
            drivers: null
        }

        this.order = this.order.bind(this);
        this.cancel = this.cancel.bind(this);
    }

    render() {
        const {drivers, request} = this.state;

        if(!drivers) {
            return <CircularProgress />
        }

        return (
            <React.Fragment>
                <Grid container alignItems="flex-start" justify="space-between" direction="row">
                    <Typography variant="h5">
                        <React.Fragment><b>Podróż z:</b> {request.src} <br/> <b>do: </b> {request.dst}</React.Fragment> <br/>
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

                <Paper style={{width: '100%', overflow: 'auto'}}>
                    <Table size="small" aria-label="a dense table">
                        <TableHead>
                            <TableRow>
                                <TableCell style={{fontWeight: "bold"}}>Kierowca</TableCell>
                                <TableCell style={{fontWeight: "bold"}}>Samochód</TableCell>
                                <TableCell style={{fontWeight: "bold"}}>Przedsiębiorstwo</TableCell>
                                <TableCell style={{fontWeight: "bold"}}>Cena</TableCell>
                                <TableCell style={{fontWeight: "bold"}}>Ocena kierowcy</TableCell>
                                <TableCell style={{fontWeight: "bold"}}></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {drivers.result.map((driver, i) => (
                                <TableRow key={driver.id}>
                                    <TableCell>{driver.driver.name + ' ' + driver.driver.surname}</TableCell>
                                    <TableCell>{driver.car.producer + ' ' + driver.car.modelName + ' ' + driver.car.productionYear}</TableCell>
                                    <TableCell>{driver.company ? driver.company.name : '-'}</TableCell>
                                    <TableCell>{driver.timeDistanceInfo.approxPrice + ' zł'}</TableCell>
                                    <TableCell>{driver.driver.rate}</TableCell>
                                    <TableCell>
                                        <Button variant="outlined" size="small" color="secondary" onClick={() => this.order(driver.id)}>
                                            Zamów
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Paper>
            </React.Fragment>
        )
    }

    componentDidMount() {
        this.fetchRideData();
    }

    fetchRideData() {
        authenticationService.request('/ride/current_request', 'GET')
            .then(request => {
                request = request.data;

                const obj = {
                    city: request.city,
                    srcLat: request.startPoint.lat,
                    srcLng: request.startPoint.lng,
                    dstLat: request.destinationPoint.lat,
                    dstLng: request.destinationPoint.lng,
                    src: request.src,
                    dst: request.dst
                };

                this.setState({
                    request: obj
                });

                this.fetchDrivers();
            })
            .catch(error => console.log(error));
    }

    fetchDrivers() {
        const {request} = this.state;

        authenticationService.request('/customer/available_drivers', 'POST', request)
            .then(drivers => {
                console.log(drivers);
                this.setState({
                    drivers: drivers.data
                });
                //authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    order(workingDriverId) {
        authenticationService.request('/ride/order/' + workingDriverId, 'GET')
            .then(result => {
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }

    cancel() {
        authenticationService.request('/ride/cancel_order', 'GET')
            .then(result => {
                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }
}

export default CustomerRideOrder;