package com.prudential.car.service;

import com.google.common.base.Strings;
import com.prudential.car.cache.CacheService;
import com.prudential.car.service.bean.OperationResult;
import com.prudential.car.dao.CarRentalRecordRepo;
import com.prudential.car.dao.CarRepo;
import com.prudential.car.dao.entity.Car;
import com.prudential.car.dao.entity.CarRentalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RentalCarService {
    @Autowired
    private CarRepo carRepo;
    @Autowired
    private CarRentalRecordRepo carRentalRecordRepo;
    @Autowired
    private CacheService cacheService;

    @Transactional
    //we can use cach to optimize it
    public List<Car> listPagedCars(int page) {
        if (page < 0) {
            return null;
        }
        PageRequest pageRequest = new PageRequest(page, 20, null);
        Page<Car> cars = carRepo.findPagedCar(pageRequest);
        return cars.getContent();
    }

    //if deploy to cluster env ,this method will be wrong,need use redis or zk
    public String getRentalRandomCode() {
        String rentalCode = UUID.randomUUID().toString();
        cacheService.addToCache(rentalCode, new Date());
        return rentalCode;
    }

    @Transactional
    public OperationResult<CarRentalRecord> rentalCar(String rentalRandomCode, long carId, Date startRentalDate, Date endRentalDate) {
        OperationResult result = new OperationResult();
        Car car = carRepo.findOne(carId);
        if (car == null) {
            result.setErrorMessage("Can't find car!");
            result.setSuccess(false);
            return result;
        }
        if(Strings.isNullOrEmpty(rentalRandomCode)||cacheService.getCachedCode(rentalRandomCode)==null){
            result.setErrorMessage("The rental code has expired!");
            result.setSuccess(false);
            return result;
        }
        if (car.getRemainCount() <= 0) {
            result.setErrorMessage("The car has rented over!");
            result.setSuccess(false);
            return result;
        }
        int updateResult = carRepo.updateCarRemainCount(carId, car.getVersion(), car.getRemainCount() - 1, car.getVersion() + 1);
        if (updateResult == 0) {
            result.setErrorMessage("Rental fail,Please try again!");
            result.setSuccess(false);
            return result;
        }
        cacheService.removeCache(rentalRandomCode);
        CarRentalRecord rentalRecord = new CarRentalRecord();
        rentalRecord.setCar(car);
        rentalRecord.setStartRentalDate(startRentalDate);
        rentalRecord.setEndRentalDate(endRentalDate);
        carRentalRecordRepo.save(rentalRecord);
        result.setSuccess(true);
        result.setResultValue(rentalRecord);
        return result;
    }


}
