package com.taxi.taxi.request;

import javax.validation.constraints.NotNull;

public class OpinionRequest {
    @NotNull
    private Long rate;

    @NotNull
    private String comment;

    @NotNull
    private Long rideId;

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
