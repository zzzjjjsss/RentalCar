package com.prudential.car.controller;

import com.prudential.car.controller.bean.*;
import com.prudential.car.service.bean.OperationResult;
import com.prudential.car.dao.entity.Car;
import com.prudential.car.dao.entity.CarRentalRecord;
import com.prudential.car.service.RentalCarService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RentalCarController {
    @Autowired
    private RentalCarService rentalCarService;

    @RequestMapping(value = "cars/page/{pageIndex}", method = RequestMethod.GET)
    @ApiOperation(value="List paged cars that can be rented!")
    public ListCarResponse listCars(@PathVariable("pageIndex") int pageIndex) {
        ListCarResponse response = new ListCarResponse();
        List<Car> cars = rentalCarService.listPagedCars(pageIndex);
        response.setCars(cars);
        response.setResultCode(ApiResponse.ResultCode.OK);
        return response;
    }

    @RequestMapping(value = "rentalCode", method = RequestMethod.GET)
    @ApiOperation(value="Generate a code that can be used to rental a car.")
    public RentalCodeResponse rentalCode() {
        RentalCodeResponse response = new RentalCodeResponse();
        String code = rentalCarService.getRentalRandomCode();
        response.setRentalCode(code);
        response.setResultCode(ApiResponse.ResultCode.OK);
        return response;
    }

    @RequestMapping(value = "rentalCar", method = RequestMethod.POST)
    @ApiOperation(value="Rental a car !")
    public RentalCarResponse rentalCar(@RequestBody RentalCarRequest rentalCarRequest) {
        RentalCarResponse response = new RentalCarResponse();
        OperationResult<CarRentalRecord> rentalResult = rentalCarService.rentalCar(rentalCarRequest.getRentalCode(), rentalCarRequest.getCarId(), rentalCarRequest.getStartRentalDate(), rentalCarRequest.getEndRenttalDate());
        if (rentalResult.isSuccess()) {
            response.setRentalCarRecordId(rentalResult.getResultValue().getId());
            response.setResultCode(ApiResponse.ResultCode.OK);
        } else {
            response.setResultCode(ApiResponse.ResultCode.FAIL);
            response.setMes(rentalResult.getErrorMessage());
        }
        return response;
    }


}
