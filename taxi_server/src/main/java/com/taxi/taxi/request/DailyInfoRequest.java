package com.taxi.taxi.request;

import javax.validation.constraints.NotNull;

public class DailyInfoRequest {
    @NotNull
    private double lat;

    @NotNull
    private double lng;

    @NotNull
    private Long carId;

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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
