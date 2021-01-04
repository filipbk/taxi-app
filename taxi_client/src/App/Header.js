import React, {Component} from "react";
import AppBar from '@material-ui/core/AppBar';
import {Toolbar, IconButton, Typography, Drawer} from "@material-ui/core";
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import MenuItem from "@material-ui/core/MenuItem";
import {authenticationService, history} from "../utils";
import {withRouter, Link} from 'react-router-dom';
import Brightness6Icon from '@material-ui/icons/Brightness6';
import Grid from "@material-ui/core/Grid";
import ExitToAppIcon from '@material-ui/icons/ExitToApp';





class Header extends Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: null,
            user: authenticationService.currentUserValue,
            drawerVisible: false
        };

        this.toggleDrawerVisibility = this.toggleDrawerVisibility.bind(this);
        this.logout = this.logout.bind(this);
    }

    logout() {
        authenticationService.logout();
        history.push('/login');
        //this.toggleDrawerVisibility();
    }

    componentDidMount() {
        authenticationService.currentUser.subscribe(x => this.setState({currentUser: x}));
    }

    render() {
        const {toggle} = this.props;
        const {currentUser} = this.state;

        return(
            <React.Fragment>
                <AppBar position="sticky" color="primary">
                    <Toolbar>
                        <Grid container alignItems="flex-start" justify="space-between" direction="row">
                            <Grid item>
                                <Link to="/" style={{ textDecoration: 'none', color: "inherit"}}>
                                    <Typography variant="h6" noWrap style={{display: 'inline-flex', lineHeight: 3}}>
                                        <b>Taxi App</b>
                                    </Typography>
                                </Link>
                            </Grid>

                            <Grid item>
                                <IconButton
                                    aria-label="account of current user"
                                    aria-controls="menu-appbar"
                                    aria-haspopup="true"
                                    onClick={toggle}
                                    color="inherit"
                                >
                                    <Brightness6Icon />
                                </IconButton>

                                {
                                    currentUser && currentUser.username ?
                                        <IconButton
                                            aria-valuetext='Wyloguj się'
                                            aria-label="account of current user"
                                            aria-controls="menu-appbar"
                                            aria-haspopup="true"
                                            onClick={this.logout}
                                            color="inherit"
                                        >
                                            <ExitToAppIcon />
                                        </IconButton>
                                        :
                                        null
                                }

                                <Typography variant="h6" noWrap style={{display: 'inline-flex'}}>
                                    <b>{currentUser && currentUser.username ? currentUser.username : null}</b>
                                </Typography>
                            </Grid>
                        </Grid>
                    </Toolbar>
                </AppBar>
                <Drawer
                    variant="persistent"
                    anchor="left"
                    open={this.state.drawerVisible}
                    width={300}
                >
                    <IconButton onClick={this.toggleDrawerVisibility}>
                        <ChevronLeftIcon />
                    </IconButton>

                    <Divider />

                    <List>
                        <Link to="/" style={{ textDecoration: 'none', color: "inherit"  }}>
                            <MenuItem onClick={this.toggleDrawerVisibility}>
                                Strona główna
                            </MenuItem>
                        </Link>
                        <Link to="/login" style={{ textDecoration: 'none', color: "inherit" }}>
                            <MenuItem onClick={this.toggleDrawerVisibility}>
                                Zaloguj się
                            </MenuItem>
                        </Link>
                        <MenuItem onClick={this.logout}>
                            Wyloguj się
                        </MenuItem>
                    </List>

                </Drawer>
            </React.Fragment>
        )
    }

    toggleDrawerVisibility() {
        this.setState({
            drawerVisible: !this.state.drawerVisible
        });
    }
}

export default withRouter(Header);