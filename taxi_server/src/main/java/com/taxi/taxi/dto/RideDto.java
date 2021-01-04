package com.taxi.taxi.dto;

import com.google.maps.model.LatLng;
import com.taxi.taxi.model.Ride;
import com.taxi.taxi.model.RideStatus;
import com.taxi.taxi.util.TimeDistanceInfo;

import java.math.BigDecimal;
import java.util.Date;

public class RideDto implements Dto {
    private Long id;

    private Date orderTime;

    private Date startTime;

    private Date endTime;

    private String price;

    private CustomerDto customer;

    private DailyInfoDto dailyInfo;

    private LatLng startPoint;

    private LatLng destinationPoint;

    private String city;

    private RideStatus rideStatus;

    private String src;

    private String dst;

    private String driverDistance;

    private String distance;

    private OpinionDto opinion;

    public RideDto(Ride ride) {
        this.id = ride.getId();
        this.orderTime = ride.getOrderTime();
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.price = ride.getPrice();
        this.customer = new CustomerDto(ride.getCustomer());
        this.startPoint = ride.getStartPoint();
        this.destinationPoint = ride.getDestinationPoint();
        this.city = ride.getCity();
        this.rideStatus = ride.getRideStatus();
        this.src = ride.getSrc();
        this.dst = ride.getDst();
        this.driverDistance = ride.getDriverDistance();
        this.distance = ride.getDistance();

        if(ride.getOpinion() != null) {
            this.opinion = new OpinionDto(ride.getOpinion());
        }

        if(ride.getDailyInfo() != null) {
            this.dailyInfo = new DailyInfoDto(ride.getDailyInfo());
        }
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

    public void setPrice(String price) {
        this.price = price;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public DailyInfoDto getDailyInfo() {
        return dailyInfo;
    }

    public void setDailyInfo(DailyInfoDto dailyInfo) {
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

    public OpinionDto getOpinion() {
        return opinion;
    }

    public void setOpinion(OpinionDto opinion) {
        this.opinion = opinion;
    }
}
