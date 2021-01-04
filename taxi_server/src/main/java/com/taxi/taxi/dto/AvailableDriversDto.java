package com.taxi.taxi.dto;

import com.google.maps.model.DistanceMatrix;
import com.taxi.taxi.request.AvailableDriversRequest;

import java.util.List;

public class AvailableDriversDto implements Dto {
    private List<DailyInfoDto> result;

    private AvailableDriversRequest availableDriversRequest;

    private DistanceMatrix distanceMatrix;

    public AvailableDriversDto(List<DailyInfoDto> result, AvailableDriversRequest availableDriversRequest, DistanceMatrix distanceMatrix) {
        this.result = result;
        this.availableDriversRequest = availableDriversRequest;
        this.distanceMatrix = distanceMatrix;
    }

    public AvailableDriversDto(List<DailyInfoDto> result, AvailableDriversRequest availableDriversRequest) {
        this.result = result;
        this.availableDriversRequest = availableDriversRequest;
    }

    public List<DailyInfoDto> getResult() {
        return result;
    }

    public void setResult(List<DailyInfoDto> result) {
        this.result = result;
    }

    public AvailableDriversRequest getAvailableDriversRequest() {
        return availableDriversRequest;
    }

    public void setAvailableDriversRequest(AvailableDriversRequest availableDriversRequest) {
        this.availableDriversRequest = availableDriversRequest;
    }

    public DistanceMatrix getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }
}
