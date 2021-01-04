import React, {Component} from "react";
import {
    Button, CircularProgress,
    Dialog, DialogActions,
    DialogContent,
    DialogTitle,
} from "@material-ui/core";
import {authenticationService} from "../utils";

class Opinion extends Component {
    constructor(props) {
        super(props);

        this.state = {
            opinion: null
        };

        this.fetchOpinion = this.fetchOpinion.bind(this);
    }

    render() {
        const {open, handleClose} = this.props;
        const {opinion} = this.state;


        return(
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" fullWidth={true}
                    maxWidth = {'md'}>
                <DialogTitle id="form-dialog-title">Opinia o przejeździe</DialogTitle>
                <DialogContent>

                    {opinion ?
                        <React.Fragment>
                        <b>Ocena:</b> {opinion.rate}<br/>
                        <b>Komentarz:</b> {opinion.comment}
                        </React.Fragment> : <CircularProgress/>
                        }

                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Zamknij
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.rideId !== this.props.rideId) {
            this.fetchOpinion();
        }
    }

    fetchOpinion() {
        const {rideId} = this.props;

        if(rideId == null) {
            return;
        }

        authenticationService.request('/opinion/ride/' + rideId, 'GET')
            .then(opinion => {

                console.log(opinion)
                this.setState({
                    opinion: opinion.data
                });
            })
            .catch(error => {
                console.log(error)
            });
    }
}

export default Opinion;