package com.taxi.taxi.request;

import com.taxi.taxi.model.User;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String usertype;
    private String username;

    public JwtAuthenticationResponse(String jwt) {
        this.accessToken = jwt;
    }

    public JwtAuthenticationResponse(String jwt, User user) {
        this.accessToken = jwt;
        this.usertype = user.getUserType();
        this.username = user.getUsername();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
