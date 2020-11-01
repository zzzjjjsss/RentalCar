package com.prudential.car.controller.bean;

import java.util.Date;

public class RentalCarRequest {
    private long carId;
    private String rentalCode;
    private Date startRentalDate;
    private Date endRenttalDate;

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getRentalCode() {
        return rentalCode;
    }

    public void setRentalCode(String rentalCode) {
        this.rentalCode = rentalCode;
    }

    public Date getStartRentalDate() {
        return startRentalDate;
    }

    public void setStartRentalDate(Date startRentalDate) {
        this.startRentalDate = startRentalDate;
    }

    public Date getEndRenttalDate() {
        return endRenttalDate;
    }

    public void setEndRenttalDate(Date endRenttalDate) {
        this.endRenttalDate = endRenttalDate;
    }
}
