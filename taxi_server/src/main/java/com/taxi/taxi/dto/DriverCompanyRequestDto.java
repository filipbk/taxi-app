package com.taxi.taxi.dto;

import com.taxi.taxi.model.DriverCompanyRequest;
import com.taxi.taxi.model.RequestStatus;

import java.util.Date;

public class DriverCompanyRequestDto implements Dto {
    private Long id;

    private Long driverId;

    private String driverName;

    private String driverSurname;

    private Long companyId;

    private String companyName;

    private Date requestTime;

    private RequestStatus requestStatus;

    public DriverCompanyRequestDto(DriverCompanyRequest driverCompanyRequest) {
        this.id = driverCompanyRequest.getId();
        this.driverId = driverCompanyRequest.getDriver().getId();
        this.driverName = driverCompanyRequest.getDriver().getName();
        this.driverSurname = driverCompanyRequest.getDriver().getSurname();
        this.companyId = driverCompanyRequest.getCompany().getId();
        this.companyName = driverCompanyRequest.getCompany().getName();
        this.requestTime = driverCompanyRequest.getRequestTime();
        this.requestStatus = driverCompanyRequest.getRequestStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
