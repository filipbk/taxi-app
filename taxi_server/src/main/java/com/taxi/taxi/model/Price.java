package com.taxi.taxi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "price")
public class Price extends DBTimestamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal firstFareDay;

    private BigDecimal secondFareDay;

    @NotNull
    private BigDecimal firstFareNight;

    private BigDecimal secondFareNight;

    private Long secondFareDistance;

    @NotNull
    private BigDecimal startPrice;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Temporal(TemporalType.TIME)
    @Column(columnDefinition = "TIME")
    private Date dayStart;

    @Temporal(TemporalType.TIME)
    @Column(columnDefinition = "TIME")
    private Date nightStart;

    @OneToMany(mappedBy = "price")
    private Set<Car> cars;

    public Price() {}

    public Price(BigDecimal firstFareDay, BigDecimal secondFareDay, BigDecimal firstFareNight, BigDecimal secondFareNight, BigDecimal startPrice, Long secondFareDistance, User user, String name, Date dayStart, Date nightStart) {
        this.firstFareDay = firstFareDay;
        this.secondFareDay = secondFareDay;
        this.firstFareNight = firstFareNight;
        this.secondFareNight = secondFareNight;
        this.startPrice = startPrice;
        this.secondFareDistance = secondFareDistance;
        this.user = user;
        this.name = name;
        this.dayStart = dayStart;
        this.nightStart = nightStart;
        this.cars = new HashSet<>();
    }

    public Price(BigDecimal firstFareDay, BigDecimal firstFareNight, BigDecimal startPrice) {
        this.firstFareDay = firstFareDay;
        this.firstFareNight = firstFareNight;
        this.startPrice = startPrice;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
