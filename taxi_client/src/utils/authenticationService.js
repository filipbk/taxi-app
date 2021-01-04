import { BehaviorSubject } from 'rxjs';
import {authHeader} from "./authHeader";

const currentUserSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('currentUser')));
const userStatusSubject = new BehaviorSubject(JSON.parse(localStorage.getItem('userStatus')));

export const authenticationService = {
    login,
    logout,
    currentUser: currentUserSubject.asObservable(),
    get currentUserValue() { return currentUserSubject.value },
    request,
    mapsApiRequest,
    signup,
    userStatus: userStatusSubject.asObservable(),
    get userStatusValue() { return userStatusSubject.value },
    fetchUserStatus
}


function login(usernameOrEmail, password) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({usernameOrEmail, password})
    };

    return fetch('/auth/signin', requestOptions)
        .then(response => response.json()
            .then(json => {
                if(!response.ok) {
                    return Promise.reject(json)
                }

                return json;
            }))
        .then(user => {
            //console.log(user)
            localStorage.setItem('currentUser', JSON.stringify(user));
            currentUserSubject.next(user);

            return user;
        });
}

function signup(url, data) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };

    return fetch(url, requestOptions)
        .then(response => response.json()
            .then(json => {
                if(!response.ok) {
                    return Promise.reject(json)
                }

                return json;
            }))
        .then(user => {
            //console.log(user)
            //localStorage.setItem('currentUser', JSON.stringify(user));
            //currentUserSubject.next(user);

            return user;
        });
}

function logout() {
    localStorage.removeItem('currentUser');
    currentUserSubject.next(null);
}

function request(url, requestType, data) {
    const requestOptions = {
        headers: authHeader(),
        method: requestType
    }

    if(data) {
        requestOptions.body = JSON.stringify(data);
    }

    return fetch(url, requestOptions)
        .then(response =>
            response.json()
            .then(json => {
                if(!response.ok) {
                    return Promise.reject(json)
                }
                return json;
            })
        );
}

function mapsApiRequest(url, requestType, data) {
    const requestOptions = {
        method: requestType
    }

    if(data) {
        requestOptions.body = JSON.stringify(data);
    }

    return fetch('https://www.googleapis.com/' + url + '?key=google_api_key', requestOptions)
        .then(response => response.json()
            .then(json => {
                if(!response.ok) {
                    return Promise.reject(json)
                }

                return json;
            }));
}

function fetchUserStatus() {
    request('/auth/status', 'GET')
        .then(status => {
            if(status.data.status !== userStatusSubject.value) {
                localStorage.setItem('userStatus', JSON.stringify(status.data.status));
                userStatusSubject.next(status.data.status);
            }

            return status.data;
        })
        .catch(error => console.log(error));
}