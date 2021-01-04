package com.taxi.taxi.model;

public enum CustomerStatus implements Status {
    FREE,
    WAITING,
    RIDE,
    RIDE_ORDER_MODE,
    WAITING_FOR_DRIVER_ACCEPT,
    DRIVER_REJECTED,
    DRIVER_ARRIVED
}
