package com.taxi.taxi.util;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.taxi.taxi.model.DailyInfo;
import com.taxi.taxi.model.Price;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceCalculator {
    private List<Price> prices;

    private Price price;

    private DistanceMatrix distanceMatrix;

    private DistanceMatrix route;

    private List<DailyInfo> freeDrivers;

    private ArrayList<TimeDistanceInfo> timeDistanceInfos;

    private float distance;

    public PriceCalculator(List<Price> prices, DistanceMatrix distanceMatrix, List<DailyInfo> freeDrivers, DistanceMatrix route) {
        this.prices = prices;
        this.distanceMatrix = distanceMatrix;
        this.freeDrivers = freeDrivers;
        this.timeDistanceInfos = new ArrayList<>();
        this.distance = (float) route.rows[0].elements[0].distance.inMeters / 1000;
        this.route = route;
    }

    public PriceCalculator(Price price, DistanceMatrix distanceMatrix) {
        this.price = price;
        this.distanceMatrix = distanceMatrix;
        this.distance = (float) distanceMatrix.rows[0].elements[0].distance.inMeters / 1000;
    }

    public ArrayList<TimeDistanceInfo> calculateApproximatePrices() {
        for(Price price : prices) {
            //Price price = prices.get(i);
            TimeDistanceInfo timeDistanceInfo = new TimeDistanceInfo();
            DistanceMatrixElement distanceMatrixElement = distanceMatrix.rows[0].elements[prices.indexOf(price)];
            Date current = new Date();

            if(compareDates(current, price.getNightStart()) && compareDates(price.getDayStart(), current)) {
                //dzień
                timeDistanceInfo.setApproxPrice(calculateApproximatePricesDay(price, distanceMatrixElement));
            } else {
                //noc
                timeDistanceInfo.setApproxPrice(calculateApproximatePricesNight(price, distanceMatrixElement));
            }

            timeDistanceInfo.setApproxDistance(distanceMatrixElement.distance.humanReadable);
            timeDistanceInfo.setApproxTime(distanceMatrixElement.duration.humanReadable);
            timeDistanceInfo.setRouteDistance(route.rows[0].elements[0].distance.humanReadable);
            timeDistanceInfos.add(timeDistanceInfo);
        }

        return timeDistanceInfos;
    }

    public TimeDistanceInfo calculateApproximatePrice() {
        TimeDistanceInfo timeDistanceInfo = new TimeDistanceInfo();
        DistanceMatrixElement distanceMatrixElement = distanceMatrix.rows[0].elements[0];
        Date current = new Date();

        if(compareDates(current, price.getNightStart()) && compareDates(price.getDayStart(), current)) {
            timeDistanceInfo.setApproxPrice(calculateApproximatePricesDay(price, distanceMatrixElement));
        } else {
            timeDistanceInfo.setApproxPrice(calculateApproximatePricesNight(price, distanceMatrixElement));
        }

        timeDistanceInfo.setApproxDistance(distanceMatrixElement.distance.humanReadable);
        timeDistanceInfo.setApproxTime(distanceMatrixElement.duration.humanReadable);


        return timeDistanceInfo;
    }

    private String calculateApproximatePricesNight(Price price, DistanceMatrixElement distanceMatrixElement) {
        //float distance = (float) distanceMatrixElement.distance.inMeters / 1000;
        BigDecimal finalPrice;

        if(distance > price.getSecondFareDistance()) {
            float rest = distance - price.getSecondFareDistance();
            finalPrice = BigDecimal.valueOf(price.getStartPrice().doubleValue() + price.getSecondFareDistance().doubleValue() * price.getFirstFareNight().doubleValue() + rest * price.getSecondFareNight().doubleValue());
        } else {
            finalPrice = BigDecimal.valueOf(price.getStartPrice().doubleValue() + distance * price.getFirstFareNight().doubleValue());
        }

        //timeDistanceInfo.setApproxPrice(String.format("%.2f", finalPrice));
        return String.format("%.2f", finalPrice);
    }

    private String calculateApproximatePricesDay(Price price, DistanceMatrixElement distanceMatrixElement) {
        //float distance = (float) distanceMatrixElement.distance.inMeters / 1000;
        BigDecimal finalPrice;

        if(distance > price.getSecondFareDistance()) {
            float rest = distance - price.getSecondFareDistance();
            finalPrice = BigDecimal.valueOf(price.getStartPrice().doubleValue() + price.getSecondFareDistance().doubleValue() * price.getFirstFareDay().doubleValue() + rest * price.getSecondFareDay().doubleValue());
        } else {
            finalPrice = BigDecimal.valueOf(price.getStartPrice().doubleValue() + distance * price.getFirstFareDay().doubleValue());
        }

        //timeDistanceInfo.setApproxPrice(String.format("%.2f", finalPrice));
        return String.format("%.2f", finalPrice);
    }

    private boolean compareDates(Date d1, Date d2) {
        DateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
        return f.format(d1).compareTo(f.format(d2)) < 0;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public DistanceMatrix getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public List<DailyInfo> getFreeDrivers() {
        return freeDrivers;
    }

    public void setFreeDrivers(List<DailyInfo> freeDrivers) {
        this.freeDrivers = freeDrivers;
    }

    public ArrayList<TimeDistanceInfo> getTimeDistanceInfos() {
        return timeDistanceInfos;
    }

    public void setTimeDistanceInfos(ArrayList<TimeDistanceInfo> timeDistanceInfos) {
        this.timeDistanceInfos = timeDistanceInfos;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
