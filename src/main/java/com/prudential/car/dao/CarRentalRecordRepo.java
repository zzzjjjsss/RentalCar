package com.prudential.car.dao;

import com.prudential.car.dao.entity.CarRentalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRecordRepo extends JpaRepository<CarRentalRecord,Long> {
}
