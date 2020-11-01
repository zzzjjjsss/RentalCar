package com.prudential.car.controller.bean;

import com.prudential.car.dao.entity.Car;

import java.util.ArrayList;
import java.util.List;

public class ListCarResponse extends ApiResponse {
    private List<Car> cars=new ArrayList<>();

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }
}
