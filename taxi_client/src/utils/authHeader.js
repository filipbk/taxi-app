import {authenticationService} from "./authenticationService";

export function authHeader() {
    const currentUser = authenticationService.currentUserValue;

    if(currentUser && currentUser.accessToken) {
        return {
            Authorization: `Bearer ${currentUser.accessToken}`,
            'Content-Type': 'application/json',
        };
    }

    return {};
}