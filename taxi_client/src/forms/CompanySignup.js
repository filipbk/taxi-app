import React, {Component} from "react";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {authenticationService} from "../utils";
import {Redirect} from "react-router";

class CompanySignup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: null,
            password: null,
            email: null,
            name: null,
            city: null,
            phoneNumber: null
        };

        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.setEmail = this.setEmail.bind(this);
        this.setName = this.setName.bind(this);
        this.setCity = this.setCity.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.setPhoneNumber = this.setPhoneNumber.bind(this);
    }

    render() {
        const currentUser = authenticationService.currentUserValue;

        if(currentUser) {
            return <Redirect to={{pathname: '/', state: {from: this.props.location}}} />
        }
        return(
            <Container>
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group controlId={'username'}>
                        <Form.Label>Nazwa użytkownika</Form.Label>
                        <Form.Control
                            type='text'
                            placeholder='Podaj nazwę użytkownika'
                            onChange={this.setUsername}
                        />
                    </Form.Group>

                    <Form.Group controlId={'password'}>
                        <Form.Label>Hasło</Form.Label>
                        <Form.Control
                            type='password'
                            placeholder='Podaj hasło'
                            onChange={this.setPassword}
                        />
                    </Form.Group>

                    <Form.Group controlId={'email'}>
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type='email'
                            placeholder='Podaj email'
                            onChange={this.setEmail}
                        />
                    </Form.Group>

                    <Form.Group controlId={'name'}>
                        <Form.Label>Nazwa firmy</Form.Label>
                        <Form.Control
                            type='text'
                            placeholder='Podaj nazwę firmy'
                            onChange={this.setName}
                        />
                    </Form.Group>

                    <Form.Group controlId={'city'}>
                        <Form.Label>Miasto</Form.Label>
                        <Form.Control
                            type='text'
                            placeholder='Wybierz miasto'
                            onChange={this.setCity}
                        />
                    </Form.Group>

                    <Form.Group controlId={'phoneNumber'}>
                        <Form.Label>Numer telefonu</Form.Label>
                        <Form.Control
                            type='text'
                            placeholder='Podaj numer telefonu'
                            onChange={this.setPhoneNumber}
                        />
                    </Form.Group>

                    <Button type="submit">
                        Zarejestruj się
                    </Button>
                </Form>
            </Container>
        )
    }

    setUsername(e) {
        this.setState({
            username: e.target.value
        });
    }

    setPassword(e) {
        this.setState({
            password: e.target.value
        });
    }

    setEmail(e) {
        this.setState({
            email: e.target.value
        });
    }

    setName(e) {
        this.setState({
            name: e.target.value
        });
    }

    setCity(e) {
        this.setState({
            city: e.target.value
        });
    }

    setPhoneNumber(e) {
        this.setState({
            phoneNumber: e.target.value
        });
    }

    handleSubmit(e) {
        e.preventDefault();
        const request = this.state;

        authenticationService.signup('/auth/company_signup', request)
            .then(user => {
                const {from} = this.props.location.state || {from: {pathname: '/'}};
                this.props.history.push(from);
                console.log(user);
            })
            .catch(error => console.log(error));
    }
}

export default CompanySignup;