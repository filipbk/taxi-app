import React, {Component} from "react";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle, FormControl, InputLabel, Select,
    TextField, Typography
} from "@material-ui/core";
import {authenticationService} from "../utils";
import Grid from "@material-ui/core/Grid";
import MenuItem from "@material-ui/core/MenuItem";

class AddCarForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            producer: null,
            modelName: null,
            productionYear: null,
            registrationNumber: null,
            color: null,
            prices: [],
            priceId: ""
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    render() {
        const {open, handleClose} = this.props;
        const {prices, priceId} = this.state;

        return(
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
                <form onSubmit={this.handleSubmit}>
                <DialogTitle id="form-dialog-title">Dodaj samochód</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Marka"
                        type="text"
                        fullWidth
                        name="producer"
                        onChange={this.handleChange}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Model"
                        type="text"
                        fullWidth
                        name="modelName"
                        onChange={this.handleChange}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Rok produkcji"
                        type="text"
                        fullWidth
                        name="productionYear"
                        onChange={this.handleChange}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Numer rejestracyjny"
                        type="text"
                        fullWidth
                        name="registrationNumber"
                        onChange={this.handleChange}
                    />
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Kolor"
                        type="text"
                        fullWidth
                        name="color"
                        onChange={this.handleChange}
                    />
                    {prices && prices.length > 0 ?
                        <FormControl fullWidth>
                            <InputLabel id="prices">Zestaw cen</InputLabel>
                            <Select
                                labelId="prices"
                                value={priceId}
                                margin="dense"
                                label="Zestaw cen"
                                fullWidth
                                name="priceId"
                                onChange={this.handleChange}
                            >
                                <MenuItem value="" key="">
                                </MenuItem>
                                {prices.map(price => (
                                    <MenuItem value={price.id} key={price.id}>
                                        {price.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        :
                        <Grid container alignItems="flex-start" justify="space-between" direction="row">
                            <Typography variant="h6" m="16px">
                                Nie posiadasz żadnego zestawu cen
                            </Typography>
                            <Button
                                style={{justifyContent: 'right'}}
                                variant="contained"
                                color="primary">
                                Dodaj zestaw cen
                            </Button>
                        </Grid>
                    }
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Anuluj
                    </Button>
                    <Button onClick={handleClose} color="primary" type="submit">
                        Dodaj
                    </Button>
                </DialogActions>
                </form>
            </Dialog>
        )
    }

    componentDidMount() {
        this.fetchPrices();
    }

    handleChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    handleSubmit(e) {
        e.preventDefault();
        const requestData = this.state;

        authenticationService
            .request('/car/create', 'POST', requestData)
            .then(result => {
                console.log(result);
                this.props.handleClose(result.data);
            })
            .catch(error => {
                console.log(error);
                this.props.handleClose();
            })
    }

    fetchPrices() {
        authenticationService.request('/prices/list', 'GET')
            .then(prices => {
                this.setState({
                    prices: prices
                });

                console.log(prices)
            })
            .catch(error => console.log(error));
    }
}

export default AddCarForm;