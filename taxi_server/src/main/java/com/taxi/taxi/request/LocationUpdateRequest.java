package com.taxi.taxi.request;

import javax.validation.constraints.NotNull;

public class LocationUpdateRequest {
    @NotNull
    private double lat;

    @NotNull
    private double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
