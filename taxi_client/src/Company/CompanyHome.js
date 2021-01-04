import React, {Component} from "react";
import {authenticationService, OrdersHistory} from "../utils";
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
import {AddCarForm, AddPriceSetForm} from "../forms";

class CompanyHome extends Component {
    constructor(props) {
        super(props);

        this.state = {
            carModalOpen: false,
            user: authenticationService.currentUserValue,
            status: null,
            cars: [],
            prices: [],
            waitingRequests: [],
            priceModalOpen: false,
        }

        this.closeModal = this.closeModal.bind(this);
        this.openModal = this.openModal.bind(this);
        this.acceptRequest = this.acceptRequest.bind(this);
        this.rejectRequest = this.rejectRequest.bind(this);
        this.openPriceModal = this.openPriceModal.bind(this);
        this.closePriceModal = this.closePriceModal.bind(this);
    }

    render() {
        const {user, status, cars, waitingRequests, prices} = this.state;

        if(!cars || !status) {
            return <CircularProgress />
        }

        return(
            <React.Fragment>
                <React.Fragment>
                    <Grid style={{marginBottom: 5}} container alignItems="flex-start" justify="space-between" direction="row">
                        <Typography variant="h5">
                            {cars.length > 0 ? "Twoje samochody:" : "Jeszcze nie posiadasz żadnego samochodu"}
                        </Typography>
                        <Button
                            onClick={this.openModal}
                            style={{justifyContent: 'right'}}
                            variant="contained"
                            color="primary">
                            Dodaj samochód
                        </Button>
                    </Grid>
                </React.Fragment>
                {cars.length > 0 ? (
                    <Paper style={{width: '100%', overflow: 'auto', marginBottom: 20}}>
                        <Table size="small" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell style={{fontWeight: "bold"}}>Marka</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Model</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Rok produkcji</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Nr rejestracyjny</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Kolor</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {cars.map(car => (
                                    <TableRow key={car.id}>
                                        <TableCell>{car.producer}</TableCell>
                                        <TableCell>{car.modelName}</TableCell>
                                        <TableCell>{car.productionYear}</TableCell>
                                        <TableCell>{car.registrationNumber}</TableCell>
                                        <TableCell>{car.color}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>
                ) : null}

                {waitingRequests && waitingRequests.length > 0 ? (
                    <React.Fragment>
                        <Typography style={{marginBottom: 5}} variant="h5">
                            Nowe prośby kierowców o dołączenie do {user.username}:
                        </Typography>

                        <Paper style={{width: '100%', overflow: 'auto', marginBottom: 20}}>
                            <Table size="small" aria-label="a dense table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell style={{fontWeight: "bold"}}>Imię</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}>Nazwisko</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}>Data</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}></TableCell>
                                        <TableCell style={{fontWeight: "bold"}}></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {waitingRequests.map(request => (
                                        <TableRow key={request.id}>
                                            <TableCell>{request.driverName}</TableCell>
                                            <TableCell>{request.driverSurname}</TableCell>
                                            <TableCell>{request.requestTime.substr(0, 10) + ' ' + request.requestTime.substr(11, 8)}</TableCell>
                                            <TableCell>
                                                <Button variant="outlined" size="small" color="secondary"
                                                        onClick={() => this.acceptRequest(request.id)}>
                                                    Zaakceptuj
                                                </Button>
                                            </TableCell>
                                            <TableCell>
                                                <Button variant="outlined" size="small" color="primary"
                                                        onClick={() => this.rejectRequest(request.id)}>
                                                    Odrzuć
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Paper>
                    </React.Fragment>
                ) : null}

                <React.Fragment>
                    <Grid style={{marginBottom: 5}} container alignItems="flex-start" justify="space-between" direction="row">
                        <Typography variant="h5" m="16px">
                            {prices && prices.length > 0 ? "Zestawy cen:" : "Nie posiadasz jeszcze żadnego zestawu cen"}
                        </Typography>
                        <Button
                            onClick={this.openPriceModal}
                            style={{justifyContent: 'right'}}
                            variant="contained"
                            color="primary">
                            Dodaj zestaw cen
                        </Button>
                    </Grid>
                </React.Fragment>
                {prices && prices.length > 0 ? (
                    <Paper style={{width: '100%', overflow: 'auto', marginBottom: 20}}>
                        <Table size="small" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell style={{fontWeight: "bold"}}>Nazwa</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>I taryfa (dzień)</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>II taryfa (noc)</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>III taryfa (dzień)</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>IV taryfa (noc)</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>II taryfa powyżej (km)</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Opłata startowa</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Początek dnia</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Początek nocy</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {prices.map(price => (
                                    <TableRow key={price.id}>
                                        <TableCell>{price.name}</TableCell>
                                        <TableCell>{price.firstFareDay}</TableCell>
                                        <TableCell>{price.firstFareNight}</TableCell>
                                        <TableCell>{price.secondFareDay}</TableCell>
                                        <TableCell>{price.secondFareNight}</TableCell>
                                        <TableCell>{price.secondFareDistance}</TableCell>
                                        <TableCell>{price.startPrice}</TableCell>
                                        <TableCell>{price.dayStart.substr(0, 5)}</TableCell>
                                        <TableCell>{price.nightStart.substr(0, 5)}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>
                ) : null}

                <OrdersHistory type='COMPANY' />

                <AddCarForm open={this.state.carModalOpen} handleClose={this.closeModal} />
                <AddPriceSetForm open={this.state.priceModalOpen} handleClose={this.closePriceModal} />
            </React.Fragment>
        )
    }

    componentDidMount() {
        this.fetchStatus();
        this.fetchCars();
        this.getNewRequests();
        this.fetchPrices();
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

    fetchCars() {
        authenticationService.request('/company/cars', 'GET')
            .then(cars => this.setState({
                cars: cars.data.cars
            }))
            .catch(error => console.log(error));
    }

    fetchPrices() {
        authenticationService.request('/prices/list', 'GET')
            .then(prices => this.setState({
                prices: prices
            }))
            .catch(error => console.log(error));
    }

    closeModal(newCar) {
        this.setState({
            carModalOpen: false
        });

        if(newCar && newCar.id) {
            this.setState(prevState => ({
                cars: [...prevState.cars, newCar]
            }));
        }
    }

    openModal() {
        this.setState({
            carModalOpen: true
        });
    }

    closePriceModal(newPrice) {
        this.setState({
            priceModalOpen: false
        });

        if(newPrice && newPrice.id) {
            this.setState(prevState => ({
                prices: [...prevState.prices, newPrice]
            }));
        }
    }

    openPriceModal() {
        this.setState({
            priceModalOpen: true
        });
    }

    getNewRequests() {
        authenticationService.request('/company/request/waiting', 'GET')
            .then(requests => {
                console.log(requests)
                this.setState({
                    waitingRequests: requests
                })
            })
            .catch(error => console.log(error));
    }

    acceptRequest(requestId) {
        authenticationService.request('/company/request/accept/' + requestId, 'GET')
            .then(cars => {
                /*this.setState({
                    cars: cars.data.cars
                })*/
                console.log(cars);
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

export default CompanyHome;