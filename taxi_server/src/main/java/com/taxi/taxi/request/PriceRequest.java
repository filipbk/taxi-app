package com.taxi.taxi.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class PriceRequest {
    @NotNull
    private BigDecimal firstFareDay;

    private BigDecimal secondFareDay;

    @NotNull
    private BigDecimal firstFareNight;

    private BigDecimal secondFareNight;

    private Long secondFareDistance;

    @NotNull
    private BigDecimal startPrice;

    @NotBlank
    private String name;

    @NotNull
    private Date dayStart;

    @NotNull
    private Date nightStart;

    public BigDecimal getFirstFareDay() {
        return firstFareDay;
    }

    public void setFirstFareDay(BigDecimal firstFareDay) {
        this.firstFareDay = firstFareDay;
    }

    public BigDecimal getSecondFareDay() {
        return secondFareDay;
    }

    public void setSecondFareDay(BigDecimal secondFareDay) {
        this.secondFareDay = secondFareDay;
    }

    public BigDecimal getFirstFareNight() {
        return firstFareNight;
    }

    public void setFirstFareNight(BigDecimal firstFareNight) {
        this.firstFareNight = firstFareNight;
    }

    public BigDecimal getSecondFareNight() {
        return secondFareNight;
    }

    public void setSecondFareNight(BigDecimal secondFareNight) {
        this.secondFareNight = secondFareNight;
    }

    public Long getSecondFareDistance() {
        return secondFareDistance;
    }

    public void setSecondFareDistance(Long secondFareDistance) {
        this.secondFareDistance = secondFareDistance;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDayStart() {
        return dayStart;
    }

    public void setDayStart(Date dayStart) {
        this.dayStart = dayStart;
    }

    public Date getNightStart() {
        return nightStart;
    }

    public void setNightStart(Date nightStart) {
        this.nightStart = nightStart;
    }
}
