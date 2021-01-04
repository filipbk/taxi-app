import * as React from "react";
import {authenticationService} from "../utils";
import {Component} from "react";
import Col from 'react-bootstrap/Col'
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import {Link, Redirect} from "react-router-dom";
import {Button, TextField} from "@material-ui/core";

class Login extends Component{
     constructor(props) {
         super(props);
         this.state = {
             usernameOrEmail: null,
             password: null
         }

         this.setUsernameOrEmail = this.setUsernameOrEmail.bind(this);
         this.setPassword = this.setPassword.bind(this);
         this.handleSubmit = this.handleSubmit.bind(this);
         this.handleChange = this.handleChange.bind(this);
     }

     render() {
         const currentUser = authenticationService.currentUserValue;

         if(currentUser) {
             return <Redirect to={{pathname: '/', state: {from: this.props.location}}} />
         }


         return(
           <Container>
            <Row>
             <Col md={12} lg={6}>
                 <form onSubmit={this.handleSubmit}>
                     <TextField
                         margin="dense"
                         label="Login"
                         type="text"
                         fullWidth
                         name="usernameOrEmail"
                         onChange={this.handleChange}
                     />
                     <TextField
                         margin="dense"
                         label="Hasło"
                         type="password"
                         fullWidth
                         name="password"
                         onChange={this.handleChange}
                     />
                     <Button style={{marginTop: 15}} color="primary" type="submit" variant="contained" fullWidth>
                         Zaloguj się
                     </Button>
                 </form>
            </Col>
            <Col md={12} lg={6}>
                <Button style={{marginTop: 15, marginBottom: 20}} to='/customer_signup' component={Link} color="primary" variant="contained" fullWidth>Zarejestruj się jako klient</Button>
                <Button style={{marginBottom: 20}} to='/driver_signup' component={Link} color="primary" variant="contained" fullWidth>Zarejestruj się jako kierowca</Button>
                <Button to='/company_signup' component={Link} color="primary" variant="contained" fullWidth>Zarejestruj przedsiębiorstwo</Button>
            </Col>
            </Row>
           </Container>
         )
     }

     setUsernameOrEmail(e) {
         this.setState({
             usernameOrEmail: e.target.value
         });
     }

     setPassword(e) {
         this.setState({
             password: e.target.value
         });
     }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

     handleSubmit(e) {
         e.preventDefault();
         const {usernameOrEmail, password} = this.state;

         authenticationService.login(usernameOrEmail, password)
             .then(user => {
                 console.log(user)
                 const {from} = this.props.location.state || {from: {pathname: '/'}};
                 this.props.history.push(from);
             })
             .catch(error => console.log(error));
     }
}

export default Login;