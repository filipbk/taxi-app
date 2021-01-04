package com.taxi.taxi.util;

public class TimeDistanceInfo {
    private String approxTime;

    private String approxPrice;

    private String approxDistance;

    private String routeDistance;

    public TimeDistanceInfo() {}

    public String getApproxTime() {
        return approxTime;
    }

    public void setApproxTime(String approxTime) {
        this.approxTime = approxTime;
    }

    public String getApproxPrice() {
        return approxPrice;
    }

    public void setApproxPrice(String approxPrice) {
        this.approxPrice = approxPrice;
    }

    public String getApproxDistance() {
        return approxDistance;
    }

    public void setApproxDistance(String approxDistance) {
        this.approxDistance = approxDistance;
    }

    public String getRouteDistance() {
        return routeDistance;
    }

    public void setRouteDistance(String routeDistance) {
        this.routeDistance = routeDistance;
    }
}
