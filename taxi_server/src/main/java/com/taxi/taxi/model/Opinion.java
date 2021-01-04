package com.taxi.taxi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Opinion extends DBTimestamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long rate;

    private String comment;

    @NotNull
    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    public Opinion() {}

    public Opinion(@NotNull Long rate, String comment, @NotNull Ride ride) {
        this.rate = rate;
        this.comment = comment;
        this.ride = ride;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
