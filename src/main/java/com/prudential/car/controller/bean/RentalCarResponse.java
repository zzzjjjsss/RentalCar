package com.prudential.car.controller.bean;

public class RentalCarResponse extends ApiResponse{
    private Long rentalCarRecordId;

    public Long getRentalCarRecordId() {
        return rentalCarRecordId;
    }

    public void setRentalCarRecordId(Long rentalCarRecordId) {
        this.rentalCarRecordId = rentalCarRecordId;
    }
}
