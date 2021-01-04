import React, {Component} from "react";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    TextField
} from "@material-ui/core";
import {authenticationService} from "../utils";
import {KeyboardTimePicker} from "@material-ui/pickers";

class AddPriceSetForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            firstFareDay: 2.4,
            secondFareDay: 2.4,
            firstFareNight: 2.4,
            secondFareNight: 2.4,
            secondFareDistance: 25,
            startPrice: 5,
            dayStart: new Date(),
            nightStart: new Date(),
            name: null
        }

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        const {open, handleClose} = this.props;

        return(
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <form onSubmit={this.handleSubmit}>
                    <DialogTitle id="form-dialog-title">Dodaj zestaw cen</DialogTitle>
                    <DialogContent>
                        <TextField
                            autoFocus
                            margin="dense"
                            label="Nazwa"
                            type="text"
                            fullWidth
                            name="name"
                            onChange={this.handleChange}
                        />
                        <TextField
                            margin="dense"
                            step={0.1}
                            defaultValue={2.40}
                            label="I taryfa (dzień)"
                            type="number"
                            fullWidth
                            name="firstFareDay"
                            onChange={this.handleChange}
                        />
                        <TextField
                            margin="dense"
                            step={0.1}
                            defaultValue={2.40}
                            label="II taryfa (noc)"
                            type="number"
                            fullWidth
                            name="firstFareNight"
                            onChange={this.handleChange}
                        />
                        <TextField
                            margin="dense"
                            step={0.1}
                            defaultValue={2.40}
                            label="III taryfa (dzień)"
                            type="number"
                            fullWidth
                            name="secondFareDay"
                            onChange={this.handleChange}
                        />
                        <TextField
                            margin="dense"
                            step={0.1}
                            defaultValue={2.40}
                            label="IV taryfa (noc)"
                            type="number"
                            fullWidth
                            name="secondFareNight"
                            onChange={this.handleChange}
                        />
                        <TextField
                            margin="dense"
                            step={0.1}
                            defaultValue={5}
                            label="Opłata startowa"
                            type="number"
                            fullWidth
                            name="startPrice"
                            onChange={this.handleChange}
                        />
                        <TextField
                            margin="dense"
                            step={0.1}
                            defaultValue={25}
                            label="II taryfa powyżej (km)"
                            type="number"
                            fullWidth
                            name="secondFareDistance"
                            onChange={this.handleChange}
                        />
                        <KeyboardTimePicker
                            ampm={false}
                            margin="dense"
                            label="Początek dnia"
                            value={this.state.dayStart}
                            fullWidth
                            name="dayStart"
                            onChange={(date, time) => this.setState({dayStart: date})}
                        />
                        <KeyboardTimePicker
                            ampm={false}
                            margin="dense"
                            label="Początek nocy"
                            value={this.state.nightStart}
                            fullWidth
                            name="nightStart"
                            onChange={(date, time) => this.setState({nightStart: date})}
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleClose} color="primary">
                            Anuluj
                        </Button>
                        <Button color="primary" onClick={this.handleSubmit}>
                            Dodaj
                        </Button>
                    </DialogActions>
                </form>
            </Dialog>
        )
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit(e) {
        e.preventDefault();
        const requestData = this.state;

        authenticationService
            .request('/prices/add', 'POST', requestData)
            .then(result => {
                console.log(result);
                this.props.handleClose(result.data);
            })
            .catch(error => {
                console.log(error);
                this.props.handleClose();
            })
    }
}

export default AddPriceSetForm;