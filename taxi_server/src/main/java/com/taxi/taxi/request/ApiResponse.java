package com.taxi.taxi.request;

import com.taxi.taxi.dto.Dto;

import java.util.List;

public class ApiResponse {
    private Boolean success;
    private String message;
    private Dto data;
    private List<? extends Dto> dataList;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success, String message, Dto data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Boolean success, String message, List<? extends Dto> dataList) {
        this.success = success;
        this.message = message;
        this.dataList = dataList;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Dto getData() {
        return data;
    }

    public void setData(Dto data) {
        this.data = data;
    }

    public List<? extends Dto> getDataList() {
        return dataList;
    }

    public void setDataList(List<Dto> dataList) {
        this.dataList = dataList;
    }
}
