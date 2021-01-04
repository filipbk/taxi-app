package com.taxi.taxi.request;

import javax.validation.constraints.NotNull;

public class AvailableDriversRequest {
    @NotNull
    private double srcLat;

    @NotNull
    private double srcLng;

    @NotNull
    private double dstLat;

    @NotNull
    private double dstLng;

    @NotNull
    private String city;

    @NotNull
    private String src;

    @NotNull
    private String dst;

    public double getSrcLat() {
        return srcLat;
    }

    public void setSrcLat(double srcLat) {
        this.srcLat = srcLat;
    }

    public double getSrcLng() {
        return srcLng;
    }

    public void setSrcLng(double srcLng) {
        this.srcLng = srcLng;
    }

    public double getDstLat() {
        return dstLat;
    }

    public void setDstLat(double dstLat) {
        this.dstLat = dstLat;
    }

    public double getDstLng() {
        return dstLng;
    }

    public void setDstLng(double dstLng) {
        this.dstLng = dstLng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
