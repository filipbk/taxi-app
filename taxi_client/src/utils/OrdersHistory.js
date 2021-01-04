import React, {Component} from "react";
import {authenticationService} from "./authenticationService";
import {Button, Paper, Typography} from "@material-ui/core";
import {Opinion, OpinionForm, Opinions} from "../forms";
import MaterialTable from "material-table";
import {tableIcons} from "./icons";

class OrdersHistory extends Component {
    constructor(props) {
        super(props);

        this.state = {
            rides: [],
            modalOpen: false,
            rideId: null,
            userId: null,
            opinionsModalOpen: false,
            opinionModal: false
        };

        this.openForm = this.openForm.bind(this);
        this.closeForm = this.closeForm.bind(this);
        this.openOpinions = this.openOpinions.bind(this);
        this.closeOpinions = this.closeOpinions.bind(this);
        this.openOpinion = this.openOpinion.bind(this);
        this.closeOpinion = this.closeOpinion.bind(this);
    }

    render() {
        const {type} = this.props;
        const {rides} = this.state;

        let table;

        if(type === 'CUSTOMER') {
            table = this.customerBody();
        } else if(type === 'DRIVER') {
            table = this.driverBody();
        } else {
            table = this.companyBody();
        }

        return(
            <React.Fragment>
                <Typography style={{marginBottom: 5}} variant="h5" m="16px">
                    {rides.length > 0 ? "Historia przejazdów:" : "Nie posiadasz jeszcze żadnego przejazdu w historii"}
                </Typography>
                {rides.length > 0 ? (
                    <Paper style={{width: '100%', overflow: 'auto', marginBottom: 20}}>
                        {table}
                    </Paper>
                ) : null}

                <OpinionForm handleClose={this.closeForm} open={this.state.modalOpen} rideId={this.state.rideId}/>
                <Opinions handleClose={this.closeOpinions} open={this.state.opinionsModalOpen} userId={this.state.userId} />
                <Opinion handleClose={this.closeOpinion} open={this.state.opinionModal} rideId={this.state.rideId} />
            </React.Fragment>
        );
    }

    customerBody() {
        const {rides} = this.state;

        return (
            <MaterialTable
                localization={{
                    body: {
                        emptyDataSourceMessage: 'Brak danych'
                    },
                    toolbar: {
                        searchTooltip: 'Szukaj'
                    },
                    pagination: {
                        labelRowsSelect: 'wierszy',
                        labelDisplayedRows: ' {from}-{to} z {count}',
                        firstTooltip: 'Pierwsza strona',
                        previousTooltip: 'Poprzednia strona',
                        nextTooltip: 'Następna strona',
                        lastTooltip: 'Ostatnia strona'
                    }
                }}
                title=''
                icons={tableIcons}
                options={{
                    search: false,
                    filtering: true,
                    toolbar: false
                }}
                columns={[
                    {title: 'Początek', field: 'src'},
                    {title: 'Cel', field: 'dst'},
                    {title: 'Samochód', field: 'dailyInfo.car', render: data => data.dailyInfo.car.producer + ' ' + data.dailyInfo.car.modelName + ' ' + data.dailyInfo.car.productionYear,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.dailyInfo.car.producer + ' ' + rowData.dailyInfo.car.modelName + ' ' + rowData.dailyInfo.car.productionYear; return str.toLowerCase().includes(term.toLowerCase());}},
                    {title: 'Kierowca', field: 'dailyInfo.driver', filtering: true, render: ride =>
                            <Button onClick={() => {
                                this.setState({userId: ride.dailyInfo.driver.id});
                                this.openOpinions();
                            }} color="primary" variant='text'>
                                {ride.dailyInfo.driver.name + ' ' + ride.dailyInfo.driver.surname}
                            </Button>,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.dailyInfo.driver.name + ' ' + rowData.dailyInfo.driver.surname; return str.toLowerCase().includes(term.toLowerCase());}
                    },
                    {title: 'Przedsiębiorstwo', field: 'company', render: ride =>
                            ride.dailyInfo.company ?
                                <Button onClick={() => {
                                    this.setState({userId: ride.dailyInfo.company.id});
                                    this.openOpinions();
                                }}
                                        color="primary" variant='text'>
                                    {ride.dailyInfo.company.name}
                                </Button>
                                : null,
                        customFilterAndSearch: (term, rowData) => { return rowData.dailyInfo.company && rowData.dailyInfo.company.name.toLowerCase().includes(term.toLowerCase());}
                    },
                    {title: 'Odleglość', field: 'distance'},
                    {title: 'Cena (PLN)', field: 'price'},
                    {title: 'Ocena', field: 'opinion',
                        render: ride =>
                            ride.opinion ?
                                <Button onClick={() => {
                                    this.setState({rideId: ride.id});
                                    this.openOpinion();
                                }} color="primary" variant='text'>
                                    {ride.opinion.rate}
                                </Button>
                                :
                                <Button onClick={() => {
                                    this.setState({rideId: ride.id});
                                    this.openForm();
                                }} color="primary" variant='text'>
                                    Oceń
                                </Button>
                    },
                ]}

                data={rides}
            />
        );
    }

    companyBody() {
        const {rides} = this.state;

        return (
            <MaterialTable
                localization={{
                    body: {
                        emptyDataSourceMessage: 'Brak danych'
                    },
                    toolbar: {
                        searchTooltip: 'Szukaj'
                    },
                    pagination: {
                        labelRowsSelect: 'wierszy',
                        labelDisplayedRows: ' {from}-{to} z {count}',
                        firstTooltip: 'Pierwsza strona',
                        previousTooltip: 'Poprzednia strona',
                        nextTooltip: 'Następna strona',
                        lastTooltip: 'Ostatnia strona'
                    }
                }}
                title=''
                icons={tableIcons}
                options={{
                    search: false,
                    filtering: true,
                    toolbar: false
                }}
                columns={[
                    {title: 'Początek', field: 'src'},
                    {title: 'Cel', field: 'dst'},
                    {title: 'Samochód', field: 'dailyInfo.car', render: data => data.dailyInfo.car.producer + ' ' + data.dailyInfo.car.modelName + ' ' + data.dailyInfo.car.productionYear,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.dailyInfo.car.producer + ' ' + rowData.dailyInfo.car.modelName + ' ' + rowData.dailyInfo.car.productionYear; return str.toLowerCase().includes(term.toLowerCase());}},
                    {title: 'Kierowca', field: 'dailyInfo.driver', filtering: true, render: ride =>
                            <Button onClick={() => {
                                this.setState({userId: ride.dailyInfo.driver.id});
                                this.openOpinions();
                            }} color="primary" variant='text'>
                                {ride.dailyInfo.driver.name + ' ' + ride.dailyInfo.driver.surname}
                            </Button>,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.dailyInfo.driver.name + ' ' + rowData.dailyInfo.driver.surname; return str.toLowerCase().includes(term.toLowerCase());}
                    },
                    {title: 'Klient', field: 'customer', render: ride => ride.customer.name + ' ' + ride.customer.surname,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.customer.name + ' ' + rowData.customer.surname; return str.toLowerCase().includes(term.toLowerCase());}
                    },
                    {title: 'Odleglość', field: 'distance'},
                    {title: 'Cena (PLN)', field: 'price'},
                    {title: 'Ocena', field: 'opinion',
                        render: ride =>
                            ride.opinion ?
                                <Button onClick={() => {
                                    this.setState({rideId: ride.id});
                                    this.openOpinion();
                                }} color="primary" variant='text'>
                                    {ride.opinion.rate}
                                </Button>
                                :
                                <Button onClick={() => {
                                    this.setState({rideId: ride.id});
                                    this.openForm();
                                }} color="primary" variant='text'>
                                    Oceń
                                </Button>
                    },
                ]}

                data={rides}
            />
        );
    }

    driverBody() {
        const {rides} = this.state;

        return (
            <MaterialTable
                localization={{
                    body: {
                        emptyDataSourceMessage: 'Brak danych'
                    },
                    toolbar: {
                        searchTooltip: 'Szukaj'
                    },
                    pagination: {
                        labelRowsSelect: 'wierszy',
                        labelDisplayedRows: ' {from}-{to} z {count}',
                        firstTooltip: 'Pierwsza strona',
                        previousTooltip: 'Poprzednia strona',
                        nextTooltip: 'Następna strona',
                        lastTooltip: 'Ostatnia strona'
                    }
                }}
                title=''
                icons={tableIcons}
                options={{
                    search: false,
                    filtering: true,
                    toolbar: false
                }}
                columns={[
                    {title: 'Początek', field: 'src'},
                    {title: 'Cel', field: 'dst'},
                    {title: 'Samochód', field: 'dailyInfo.car', render: data => data.dailyInfo.car.producer + ' ' + data.dailyInfo.car.modelName + ' ' + data.dailyInfo.car.productionYear,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.dailyInfo.car.producer + ' ' + rowData.dailyInfo.car.modelName + ' ' + rowData.dailyInfo.car.productionYear; return str.toLowerCase().includes(term.toLowerCase());}},
                    {title: 'Klient', field: 'customer', render: ride => ride.customer.name + ' ' + ride.customer.surname,
                        customFilterAndSearch: (term, rowData) => {let str = rowData.customer.name + ' ' + rowData.customer.surname; return str.toLowerCase().includes(term.toLowerCase());}
                    },
                    {title: 'Przedsiębiorstwo', field: 'company', render: ride =>
                            ride.dailyInfo.company ?
                                <Button onClick={() => {
                                    this.setState({userId: ride.dailyInfo.company.id});
                                    this.openOpinions();
                                }}
                                        color="primary" variant='text'>
                                    {ride.dailyInfo.company.name}
                                </Button>
                                : null,
                        customFilterAndSearch: (term, rowData) => { return rowData.dailyInfo.company && rowData.dailyInfo.company.name.toLowerCase().includes(term.toLowerCase());}
                    },
                    {title: 'Odleglość', field: 'distance'},
                    {title: 'Cena (PLN)', field: 'price'},
                    {title: 'Ocena', field: 'opinion',
                        render: ride =>
                            ride.opinion ?
                                <Button onClick={() => {
                                    this.setState({rideId: ride.id});
                                    this.openOpinion();
                                }} color="primary" variant='text'>
                                    {ride.opinion.rate}
                                </Button>
                                :
                                <Button onClick={() => {
                                    this.setState({rideId: ride.id});
                                    this.openForm();
                                }} color="primary" variant='text'>
                                    Oceń
                                </Button>
                    },
                ]}

                data={rides}
            />
        );
    }

    componentDidMount() {
        this.fetchRidesHistory();
    }

    openForm() {
        this.setState({
            modalOpen: true
        });
    }

    closeForm() {
        this.setState({
            modalOpen: false
        });
    }

    openOpinions() {
        this.setState({
            opinionsModalOpen: true
        });
    }

    closeOpinions() {
        this.setState({
            opinionsModalOpen: false
        });
    }

    openOpinion() {
        this.setState({
            opinionModal: true
        });
    }

    closeOpinion() {
        this.setState({
            opinionModal: false
        });
    }

    fetchRidesHistory() {
        authenticationService.request('/ride/rides_history', 'GET')
            .then(rides => {
                this.setState({
                    rides: rides.dataList
                });

                console.log(rides)
            })
            .catch(error => console.log(error));
    }
}

export default OrdersHistory;