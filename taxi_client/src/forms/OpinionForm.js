import React, {Component} from "react";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle, FormControl,
    InputLabel,
    Select,
    TextField
} from "@material-ui/core";
import MenuItem from "@material-ui/core/MenuItem";
import {authenticationService} from "../utils";

class OpinionForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            rate: null,
            comment: null
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        const {open, handleClose} = this.props;

        return(
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" fullWidth={true}
                    maxWidth = {'md'}>
                <DialogTitle id="form-dialog-title">Oceń przejazd</DialogTitle>
                <DialogContent>
                    <FormControl fullWidth>
                        <InputLabel id="rate">Ocena</InputLabel>
                        <Select
                            autoFocus
                            labelId="rate"
                            margin="dense"
                            label="Ocena"
                            fullWidth
                            name="rate"
                            onChange={this.handleChange}
                        >
                            <MenuItem value="" key="">Ocena
                            </MenuItem>
                            <MenuItem value='1' key={1}>1</MenuItem>
                            <MenuItem value='2' key={2}>2</MenuItem>
                            <MenuItem value='3' key={3}>3</MenuItem>
                            <MenuItem value='4' key={4}>4</MenuItem>
                            <MenuItem value='5' key={5}>5</MenuItem>

                        </Select>
                    </FormControl>

                    <TextField
                        margin="dense"
                        label="Komentarz"
                        type="text"
                        fullWidth
                        name="comment"
                        onChange={this.handleChange}
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
            </Dialog>
        )
    }

    handleSubmit(e) {
        e.preventDefault();
        const {rideId} = this.props;
        const request = this.state;
        request.rideId = rideId;

        authenticationService
            .request('/opinion/add', 'POST', request)
            .then(result => {
                console.log(result);
                this.props.handleClose();
            })
            .catch(error => {
                console.log(error);
            });
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }
}

export default OpinionForm;