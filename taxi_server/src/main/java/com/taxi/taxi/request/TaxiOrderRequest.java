package com.taxi.taxi.request;

import javax.validation.constraints.NotNull;

public class TaxiOrderRequest {
    @NotNull
    private Long dailyInfoId;

    @NotNull
    private double lat;

    @NotNull
    private double lng;

    @NotNull
    private String city;

    public Long getDailyInfoId() {
        return dailyInfoId;
    }

    public void setDailyInfoId(Long dailyInfoId) {
        this.dailyInfoId = dailyInfoId;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
