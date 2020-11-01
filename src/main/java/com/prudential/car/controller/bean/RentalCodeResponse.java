package com.prudential.car.controller.bean;

public class RentalCodeResponse extends ApiResponse {
    private String rentalCode;

    public String getRentalCode() {
        return rentalCode;
    }

    public void setRentalCode(String rentalCode) {
        this.rentalCode = rentalCode;
    }
}
