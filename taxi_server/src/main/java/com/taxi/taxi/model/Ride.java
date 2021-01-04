package com.taxi.taxi.model;

import com.google.maps.model.LatLng;
import com.taxi.taxi.util.TimeDistanceInfo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ride extends DBTimestamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME")
    private Date orderTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATETIME")
    private Date endTime;

    private String price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "daily_info_id")
    private DailyInfo dailyInfo;

    @NotNull
    private LatLng startPoint;

    @NotNull
    private LatLng destinationPoint;

    @NotNull
    private String city;

    @NotNull
    private String src;

    @NotNull
    private String dst;

    @NotNull
    private RideStatus rideStatus;

    private String driverDistance;

    private String distance;

    @OneToOne(mappedBy = "ride")
    private Opinion opinion;

    public Ride() {}

    public Ride(Customer customer, DailyInfo dailyInfo, LatLng startPoint, LatLng destinationPoint, String city, String src, String dst, String distance) {
        this.orderTime = new Date();
        this.customer = customer;
        this.dailyInfo = dailyInfo;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.city = city;
        this.rideStatus = RideStatus.NEW;
        this.src = src;
        this.dst = dst;
        this.distance = distance;
    }

    public Ride(Customer customer, LatLng startPoint, LatLng destinationPoint, String city, String src, String dst, String distance) {
        this.orderTime = new Date();
        this.customer = customer;
        this.startPoint = startPoint;
        this.destinationPoint = destinationPoint;
        this.city = city;
        this.rideStatus = RideStatus.NEW;
        this.src = src;
        this.dst = dst;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String approxPrice) {
        this.price = approxPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DailyInfo getDailyInfo() {
        return dailyInfo;
    }

    public void setDailyInfo(DailyInfo dailyInfo) {
        this.dailyInfo = dailyInfo;
    }

    public LatLng getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(LatLng startPoint) {
        this.startPoint = startPoint;
    }

    public LatLng getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(LatLng destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public String getDriverDistance() {
        return driverDistance;
    }

    public void setDriverDistance(String driverDistance) {
        this.driverDistance = driverDistance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
