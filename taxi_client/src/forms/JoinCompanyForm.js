import React, {Component} from "react";
import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Paper,
    Table, TableBody, TableCell,
    TableHead, TableRow,
    TextField
} from "@material-ui/core";
import {authenticationService} from "../utils";

class JoinCompanyForm extends Component {
    constructor(props) {
        super(props);

        this.state = {
            companies: null
        }

        this.handleChange = this.handleChange.bind(this);
        this.sendJoinRequest = this.sendJoinRequest.bind(this);
    }

    render() {
        const {open, handleClose} = this.props;
        const {companies} = this.state;

        return(
            <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title" fullWidth={true}
                    maxWidth = {'md'}>
                <DialogTitle id="form-dialog-title">Dołącz do firmy</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Podaj miasto"
                        type="text"
                        fullWidth
                        name="city"
                        onChange={this.handleChange}
                    />
                </DialogContent>

                <DialogContent>
                    {companies && companies.length > 0 ? (
                        <Paper style={{width: '100%', overflow: 'auto'}}>
                            <Table>
                                <TableHead>
                                    <TableRow>
                                        <TableCell style={{fontWeight: "bold"}}>Miasto</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}>Nazwa firmy</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}>Ocena</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}>Liczba ocen</TableCell>
                                        <TableCell style={{fontWeight: "bold"}}></TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {companies.map(company => (
                                        <TableRow key={company.id}>
                                            <TableCell>{company.city}</TableCell>
                                            <TableCell>{company.name}</TableCell>
                                            <TableCell>{company.rate}</TableCell>
                                            <TableCell>{company.ratesNumber}</TableCell>
                                            <TableCell>
                                                <Button variant="outlined" size="small" color="secondary"
                                                        onClick={() => this.sendJoinRequest(company.id)}>
                                                    Wyślij zgłoszenie
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Paper>
                    ) : null}
                </DialogContent>

                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Anuluj
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }

    handleChange(e) {
        //this.setState({[e.target.name]: e.target.value});
        const requestData = {
            city: e.target.value
        }

        authenticationService
            .request('/company/like', 'POST', requestData)
            .then(result => {
                console.log(result);
                this.setState({
                    companies: result
                });
            })
            .catch(error => {
                console.log(error);
            })
    }

    sendJoinRequest(companyId) {
        authenticationService
            .request('/company/request/' + companyId, 'GET')
            .then(result => {
                console.log(result);
                this.props.handleClose();
            })
            .catch(error => {
                console.log(error);
            })
    }
}

export default JoinCompanyForm;