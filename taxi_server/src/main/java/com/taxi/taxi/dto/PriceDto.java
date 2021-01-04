package com.taxi.taxi.dto;

import com.taxi.taxi.model.Price;

import java.math.BigDecimal;
import java.util.Date;

public class PriceDto implements Dto {
    private Long id;

    private BigDecimal firstFareDay;

    private BigDecimal secondFareDay;

    private BigDecimal firstFareNight;

    private BigDecimal secondFareNight;

    private Long secondFareDistance;

    private BigDecimal startPrice;

    private String name;

    private Date dayStart;

    private Date nightStart;

    public PriceDto(Price price) {
        this.id = price.getId();
        this.firstFareDay = price.getFirstFareDay();
        this.secondFareDay = price.getSecondFareDay();
        this.firstFareNight = price.getFirstFareNight();
        this.secondFareNight = price.getSecondFareNight();
        this.secondFareDistance = price.getSecondFareDistance();
        this.startPrice = price.getStartPrice();
        this.name = price.getName();
        this.dayStart = price.getDayStart();
        this.nightStart = price.getNightStart();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
