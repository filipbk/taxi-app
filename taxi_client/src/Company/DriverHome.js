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
import {AddCarForm, AddPriceSetForm, JoinCompanyForm} from "../forms";
import WorkMode from "./WorkMode";
import {DriveToClient, RequestMode} from "../Driver";

class DriverHome extends Component {

    constructor(props) {
        super(props);

        this.state = {
            user: authenticationService.currentUserValue,
            status: null,
            cars: [],
            carsFromCompanies: [],
            location: null,
            position: null,
            prices: [],
            redirect: false,
            carModalOpen: false,
            joinCompanyModalOpen: false,
            priceModalOpen: false,
        };

        this.startDriving = this.startDriving.bind(this);
        this.closeModal = this.closeModal.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeJoinCompanyModal = this.closeJoinCompanyModal.bind(this);
        this.openJoinCompanyModal = this.openJoinCompanyModal.bind(this);
        this.openPriceModal = this.openPriceModal.bind(this);
        this.closePriceModal = this.closePriceModal.bind(this);
        this.getLocation = this.getLocation.bind(this);
    }

    componentDidMount() {
        this.fetchStatus();
        this.fetchCars();
        this.getLocation();
        this.fetchPrices();
    }

    render() {
        const {user} = this.state
        const {status} = this.props;

        if(!user || !status) {
            return <CircularProgress/>
        }

        if(status === 'WORK') {
            return <WorkMode />
        } else if(status === 'REQUEST') {
            return <RequestMode />
        } else if(status === 'NAVIGATION' || status === 'DRIVE') {
            return <DriveToClient status={status}/>
        }

        if(status == null) {
            return (
                <React.Fragment>
                    <CircularProgress/>
                </React.Fragment>
            )
        }

        if(status === 'FREE') {
            return this.freeDriver();
        }

        return(
            <React.Fragment>
                {user.username}
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

    fetchCars() {
        authenticationService.request('/driver/cars', 'GET')
            .then(cars => this.setState({
                cars: cars.data.cars,
                carsFromCompanies: cars.data.carsFromCompanies
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

    freeDriver() {
        const {cars, carsFromCompanies, prices} = this.state;

        if(!cars) {
            return <CircularProgress />
        }

        //console.log(this.state.location)

        const tableHead = (
            <React.Fragment>
                <TableCell style={{fontWeight: "bold"}}>Marka</TableCell>
                <TableCell style={{fontWeight: "bold"}}>Model</TableCell>
                <TableCell style={{fontWeight: "bold"}}>Rok produkcji</TableCell>
                <TableCell style={{fontWeight: "bold"}}>Nr rejestracyjny</TableCell>
                <TableCell style={{fontWeight: "bold"}}>Kolor</TableCell>
                <TableCell style={{fontWeight: "bold"}}></TableCell>
            </React.Fragment>
        );

        return(
            <React.Fragment>
                <React.Fragment>
                    <Grid style={{marginBottom: 5}} container alignItems="flex-start" justify="space-between" direction="row">
                        <Typography variant="h5">
                            {cars.length > 0 ? "Twoje samochody:" : "Nie posiadasz własnych samochodów"}
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
                                    {tableHead}
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
                                        <TableCell>
                                            <Button variant="outlined" size="small" color="secondary" onClick={() => this.startDriving(car.id)}>
                                                Jedź
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>
                ) : null}

                <React.Fragment>
                    <Grid style={{marginBottom: 5}} container alignItems="flex-start" justify="space-between" direction="row">
                        <Typography variant="h5" m="16px">
                            {carsFromCompanies.length > 0 ? "Samochody firm, w których pracujesz:" : "Obecnie nie pracujesz dla żadnej firmy"}
                        </Typography>
                        <Button
                            onClick={this.openJoinCompanyModal}
                            style={{justifyContent: 'right'}}
                            variant="contained"
                            color="primary">
                            Dołącz do firmy
                        </Button>
                    </Grid>
                </React.Fragment>
                {carsFromCompanies.length > 0 ? (
                    <Paper style={{width: '100%', overflow: 'auto', marginBottom: 20}}>
                        <Table size="small" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell style={{fontWeight: "bold"}}>Nazwa firmy</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Miasto</TableCell>
                                    {tableHead}
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {carsFromCompanies.map(company => (
                                    company.cars.map(car => (
                                        <TableRow key={car.id}>
                                            <TableCell>{company.name}</TableCell>
                                            <TableCell>{company.city}</TableCell>
                                            <TableCell>{car.producer}</TableCell>
                                            <TableCell>{car.modelName}</TableCell>
                                            <TableCell>{car.productionYear}</TableCell>
                                            <TableCell>{car.registrationNumber}</TableCell>
                                            <TableCell>{car.color}</TableCell>
                                            <TableCell>
                                                <Button variant="outlined" size="small" color="secondary"
                                                        onClick={() => this.startDriving(car.id)}>
                                                    Jedź
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                    ))
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>
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

                <OrdersHistory type='DRIVER' />


                <AddCarForm open={this.state.carModalOpen} handleClose={this.closeModal} />
                <JoinCompanyForm open={this.state.joinCompanyModalOpen} handleClose={this.closeJoinCompanyModal} />
                <AddPriceSetForm open={this.state.priceModalOpen} handleClose={this.closePriceModal} />
            </React.Fragment>
        )
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

    closeJoinCompanyModal() {
        this.setState({
            joinCompanyModalOpen: false
        });
    }

    openJoinCompanyModal() {
        this.setState({
            joinCompanyModalOpen: true
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

    startDriving(carId) {
        const {location} = this.state;
        const request = {
            carId,
            lat: location.lat,
            lng: location.lng
        };

        authenticationService.request('/driver/start_driving', 'POST', request)
            .then(result => {
                console.log(result);
                this.setState({
                    redirect: true
                });

                authenticationService.fetchUserStatus();
            })
            .catch(error => console.log(error));
    }
}

export default DriverHome;