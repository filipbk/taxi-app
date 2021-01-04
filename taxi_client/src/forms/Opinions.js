import React, {Component} from "react";
import {authenticationService} from "../utils";
import {
    Button, Dialog,
    DialogActions,
    DialogContent,
    DialogTitle, Paper, Table, TableBody, TableCell, TableHead, TableRow,
} from "@material-ui/core";

class Opinions extends Component {
    constructor(props) {
        super(props);

        this.state = {
            opinions: []
        };

        this.fetchOpinions = this.fetchOpinions.bind(this);
    }

    render() {
        const {open, handleClose} = this.props;
        const {opinions} = this.state;


        return(
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" fullWidth={true}
                    maxWidth = {'md'}>
                <DialogTitle id="form-dialog-title">Opinie o użytkowniku</DialogTitle>
                <DialogContent>

                    <Paper style={{width: '100%', overflow: 'auto'}}>
                        <Table size="small" aria-label="a dense table">
                            <TableHead>
                                <TableRow>
                                    <TableCell style={{fontWeight: "bold"}}>Ocena</TableCell>
                                    <TableCell style={{fontWeight: "bold"}}>Komentarz</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {opinions.map(opinion => (
                                    <TableRow key={opinion.id}>
                                        <TableCell>{opinion.rate}</TableCell>
                                        <TableCell>{opinion.comment}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>

                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Anuluj
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.userId !== this.props.userId) {
            this.fetchOpinions();
        }
    }

    fetchOpinions() {
        const {userId} = this.props;

        if(userId == null) {
            return;
        }

        authenticationService.request('/opinion/user/' + userId, 'GET')
            .then(opinions => {

                console.log(opinions)
                this.setState({
                    opinions: opinions.dataList
                });
            })
            .catch(error => {
                console.log(error)
            });
    }
}

export default Opinions;