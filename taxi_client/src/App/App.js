import React from 'react';
import {Route, Router, Switch} from 'react-router-dom';
import {authenticationService, history} from "../utils";
import Header from "./Header";
import {PrivateRoute} from "../forms/PrivateRoute";
import {DriverWork, HomePage} from "../Company";
import {amber, orange} from "@material-ui/core/colors";
import {ThemeProvider, createMuiTheme} from '@material-ui/core/styles'
import {Box, Container, CssBaseline} from "@material-ui/core";
import {MuiPickersUtilsProvider} from "@material-ui/pickers"
import DateFnsUtils from "@date-io/date-fns";
import frLocale from "date-fns/locale/fr";
import {CompanySignup, CustomerSignup, DriverSignup, Login} from "../forms";

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: null,
            statusInterval: null,
            user: authenticationService.currentUserValue,
            mode: 'light'
        };

        this.toggleNightMode = this.toggleNightMode.bind(this);
    }

    componentDidMount() {
        authenticationService.currentUser.subscribe(x => this.setState({currentUser: x}));
        /*let statusInterval = setInterval(authenticationService.fetchUserStatus, 10000);
        this.setState(statusInterval);*/
    }

    logout() {
        authenticationService.logout();
        history.push('login');
        /*const {statusInterval} = this.state;
        clearInterval(statusInterval);*/
    }

    toggleNightMode() {
        let {mode} = this.state;

        if(mode === 'light') {
            mode = 'dark';
        } else {
            mode = 'light';
        }

        this.setState({mode});
    }

    render() {
        const theme = createMuiTheme({
            palette: {
                primary: amber,
                secondary: orange,
                type: this.state.mode
            },
        });

        return (
            <MuiPickersUtilsProvider utils={DateFnsUtils} locale={frLocale}>
            <Router history={history}>
                <ThemeProvider theme={theme}>
                    <CssBaseline />
                    <div className="App">
                        <Header toggle={this.toggleNightMode} />
                        <Box px={{md: 3}} py={3}>
                            <Container>
                            <Switch>
                                <PrivateRoute path='/' exact component={HomePage} />
                                <Route  path='/login' exact component={Login} />
                                <Route  path='/customer_signup' exact component={CustomerSignup} />
                                <Route  path='/driver_signup' exact component={DriverSignup} />
                                <Route  path='/company_signup' exact component={CompanySignup} />
                                <PrivateRoute  path='/driving' exact component={DriverWork} />
                            </Switch>
                            </Container>
                        </Box>
                    </div>
                </ThemeProvider>
            </Router>
            </MuiPickersUtilsProvider>
        );
    }
}

export default App;
